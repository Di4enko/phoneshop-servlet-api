package com.es.phoneshop.DAO;

import com.es.phoneshop.model.order.Order;

public interface OrderDao {
    void save(Order order);
    Order getOrder(Long id);
    Order getOrderBySecureID(String id);
}
