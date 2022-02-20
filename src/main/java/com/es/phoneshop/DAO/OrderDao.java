package com.es.phoneshop.DAO;

import com.es.phoneshop.exception.OrderNotFoundException;
import com.es.phoneshop.model.order.Order;

public interface OrderDao {
    Order getOrder(Long id) throws OrderNotFoundException;
    Order getOrderBySecureID(String id) throws OrderNotFoundException;
    void save(Order order);
}
