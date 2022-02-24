package com.es.phoneshop.DAO.impl;

import com.es.phoneshop.DAO.GenericArrayListDao;
import com.es.phoneshop.DAO.ProductDao;
import com.es.phoneshop.exception.ItemNotFoundException;
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

public class ArrayListProductDao extends GenericArrayListDao<Product> implements ProductDao {
    private static  ProductDao instance;

    private ArrayListProductDao() {
        super();
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
    public List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder) {
        getLock().readLock().lock();
        try {
            Stream<Product> mainStream = getItems().stream()
                    .filter(product -> query == null || query.isEmpty() || Arrays.stream(query.split(" ")).anyMatch(product.getDescription()::contains))
                    .filter(product -> product.getPrice() != null)
                    .filter(product -> product.getStock() > 0);
            if (query != null && !query.isEmpty()) {
                Stream<Product> dopStream = getItems().stream()
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
            getLock().readLock().unlock();
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
        saveItem(product);
    }

    @Override
    public Product getProduct(Long id) {
        try {
            return getItem(id);
        } catch (ItemNotFoundException ex) {
            throw new ProductNotFoundException("Product with " + ex.getLocalizedMessage() + "not found");
        }
    }
}
