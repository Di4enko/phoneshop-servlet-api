package com.es.phoneshop.DAO.impl;

import com.es.phoneshop.DAO.GenericArrayListDao;
import com.es.phoneshop.DAO.OrderDao;
import com.es.phoneshop.exception.ItemNotFoundException;
import com.es.phoneshop.exception.OrderNotFoundException;
import com.es.phoneshop.model.order.Order;

public class ArrayListOrderDao extends GenericArrayListDao<Order> implements OrderDao {
    private static OrderDao instance;

    private ArrayListOrderDao() {
        super();
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
    public Order getOrder(Long id) {
        try {
            return getItem(id);
        } catch (ItemNotFoundException ex) {
            throw new OrderNotFoundException("Order with" + ex.getLocalizedMessage() + "id not found");
        }
    }

    @Override
    public Order getOrderBySecureID(String id) {
        getLock().readLock().lock();
        try {
            if (id == null) {
                throw new  IllegalArgumentException("ID not set");
            }
            return getItems().stream()
                    .filter(order -> id.equals(order.getSecureID()))
                    .findAny()
                    .orElseThrow(() -> new OrderNotFoundException("Order with " + id + " ID does not exist"));
        } finally {
            getLock().readLock().unlock();
        }
    }

    @Override
    public void save(Order order) {
        saveItem(order);
    }
}
