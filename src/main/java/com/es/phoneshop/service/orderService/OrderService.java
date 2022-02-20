package com.es.phoneshop.service.orderService;

import com.es.phoneshop.enums.PaymentMethod;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.order.Order;

import java.util.List;

public interface OrderService {
    Order getOrder(Cart cart);
    List<PaymentMethod> getPaymentMethod();
    void setOrder(Order order);
}
