package com.es.phoneshop.DAO.impl;

import com.es.phoneshop.DAO.ProductDao;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.enums.SortField;
import com.es.phoneshop.enums.SortOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArrayListProductDao implements ProductDao {
    private static  ProductDao instance;
    private List<Product> productList;
    private ReadWriteLock lock;
    private long maxID;


    private ArrayListProductDao() {
        productList = new ArrayList<>();
        lock = new ReentrantReadWriteLock();
        maxID = 1;
    }

    public static ProductDao getInstance() {
        if (instance == null) {
            synchronized (ArrayListProductDao.class) {
                if (instance == null) {
                    instance = new ArrayListProductDao();
                }
            }
        }
        return instance;
    }

    @Override
    public Product getProduct(Long id) throws ProductNotFoundException {
        lock.readLock().lock();
        try {
            if (id == null) {
            throw new  IllegalArgumentException("ID not set");
            }
            return productList.stream()
                    .filter(product -> id.equals(product.getId()))
                    .findAny()
                    .orElseThrow(() -> new ProductNotFoundException("Product with this ID does not exist"));
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder) {
        lock.readLock().lock();
        try {
            Stream<Product> mainStream = productList.stream()
                    .filter(product -> query == null || query.isEmpty() || Arrays.stream(query.split(" ")).anyMatch(product.getDescription()::contains))
                    .filter(product -> product.getPrice() != null)
                    .filter(product -> product.getStock() > 0);
            if (query != null && !query.isEmpty()) {
                Stream<Product> dopStream = productList.stream()
                        .filter(product -> Arrays.stream(query.split(" ")).allMatch(product.getDescription()::contains));
                return Stream.concat(dopStream, mainStream).distinct()
                        .sorted(comparator(sortField, sortOrder))
                        .collect(Collectors.toList());
            } else {
                return mainStream
                        .sorted(comparator(sortField, sortOrder))
                        .collect(Collectors.toList());
            }
        } finally {
            lock.readLock().unlock();
        }
    }

    private Comparator<Product> comparator(SortField sortField, SortOrder sortOrder) {
        Comparator<Product> comparator= Comparator.comparing(product -> {
            if (SortField.DESCRIPTION == sortField) {
                return (Comparable) product.getDescription();
            } else if (SortField.PRICE == sortField) {
                return (Comparable) product.getPrice();
            } else {
                return (Comparable) product.getClass().toString();
            }
        });
        if (SortOrder.ASC == sortOrder || SortOrder.DEFAULT == sortOrder) {
            return comparator;
        } else {
            return comparator.reversed();
        }
    }

    @Override
    public void save(Product product) {
        lock.writeLock().lock();
        try {
            if (product.getId() == null) {
                product.setId(maxID++);
                productList.add(product);
            } else if (!product.equals(getProduct(product.getId()))) {
                productList.set(Math.toIntExact(product.getId()-1), product);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void delete(Long id) throws ProductNotFoundException {
        lock.writeLock().lock();
        try {
            if (id == null) {
            throw new IllegalArgumentException("ID not set");
            }
            if (id > 0 && id <= maxID) {
                productList.remove(Math.toIntExact(id) - 1);
                rewriteID(id);
                --this.maxID;
            } else {throw new ProductNotFoundException("Product with this ID does not exist");}
        } finally {
            lock.writeLock().unlock();
        }
    }

    private void rewriteID(Long id) {
    productList.stream()
            .filter(product -> product.getId() > id)
            .forEach(product -> product.setId(product.getId() - 1));
    }
}
