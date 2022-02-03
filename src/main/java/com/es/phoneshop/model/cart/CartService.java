package com.es.phoneshop.model.cart;

import com.es.phoneshop.exception.OutOfStockException;

import javax.servlet.http.HttpServletRequest;

public interface CartService {
    Cart getCart(HttpServletRequest request);

    void add(Cart cart, Long ProductID, int quantity) throws OutOfStockException;
}
