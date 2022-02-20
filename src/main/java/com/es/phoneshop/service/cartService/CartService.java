package com.es.phoneshop.service.cartService;

import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;

import javax.servlet.http.HttpServletRequest;

public interface CartService {
    Cart getCart(HttpServletRequest request);
    void add(Cart cart, Long productID, int quantity) throws OutOfStockException;
    void update(Cart cart, Long productID, int quantity) throws OutOfStockException;
    void delete(Cart cart, Long productID);
    void clear(Cart cart);
}
