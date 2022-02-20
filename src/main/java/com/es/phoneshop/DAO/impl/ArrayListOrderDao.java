package com.es.phoneshop.DAO.impl;

import com.es.phoneshop.DAO.OrderDao;
import com.es.phoneshop.exception.OrderNotFoundException;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.product.Product;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ArrayListOrderDao implements OrderDao {
    private static  OrderDao instance;
    private List<Order> orderList;
    private ReadWriteLock lock;
    private long maxID;

    private ArrayListOrderDao() {
        orderList = new LinkedList<>();
        lock = new ReentrantReadWriteLock();
        maxID = 1;
    }

    public static OrderDao getInstance() {
        if (instance == null) {
            synchronized (ArrayListOrderDao.class) {
                if (instance == null) {
                    instance = new ArrayListOrderDao();
                }
            }
        }
        return instance;
    }


    @Override
    public Order getOrder(Long id) throws OrderNotFoundException {
        lock.readLock().lock();
        try {
            if (id == null) {
                throw new  IllegalArgumentException("ID not set");
            }
            return orderList.stream()
                    .filter(order -> id.equals(order.getId()))
                    .findAny()
                    .orElseThrow(() -> new ProductNotFoundException("Order with this ID does not exist"));
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public Order getOrderBySecureID(String id) throws OrderNotFoundException {
        lock.readLock().lock();
        try {
            if (id == null) {
                throw new  IllegalArgumentException("ID not set");
            }
            return orderList.stream()
                    .filter(order -> id.equals(order.getSecureID()))
                    .findAny()
                    .orElseThrow(() -> new ProductNotFoundException("Order with this ID does not exist"));
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void save(Order order) {
        lock.writeLock().lock();
        try {
            if (order.getId() == null) {
                order.setId(maxID++);
                orderList.add(order);
            } else if (!order.equals(getOrder(order.getId()))) {
                orderList.set(Math.toIntExact(order.getId()-1), order);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }
}
