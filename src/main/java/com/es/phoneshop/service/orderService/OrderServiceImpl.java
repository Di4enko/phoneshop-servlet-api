package com.es.phoneshop.service.orderService;

import com.es.phoneshop.DAO.OrderDao;
import com.es.phoneshop.DAO.impl.ArrayListOrderDao;
import com.es.phoneshop.enums.PaymentMethod;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.service.cartService.CartServiceImp.CartServiceImpl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService{
    private OrderDao orderDao;
    private static OrderService instance;

    private OrderServiceImpl() {
        orderDao = ArrayListOrderDao.getInstance();
    }

    public static OrderService getInstance() {
        if (instance == null) {
            synchronized (CartServiceImpl.class) {
                if (instance == null) {
                    instance = new OrderServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public Order getOrder(Cart cart) {
        synchronized (cart) {
            Order order = new Order();
            order.setItems(cart.getItems().stream().map(item -> {
                try {
                    return (CartItem) item.clone();
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList()));
            order.setTotalQuantity(cart.getTotalQuantity());
            order.setDeliveryCost(calculateDeliveryCost());
            order.setSubtotal(cart.getTotalCost());
            order.setTotalCost(order.getSubtotal().add(order.getDeliveryCost()));
            return order;
        }
    }

    @Override
    public List<PaymentMethod> getPaymentMethod() {
        return Arrays.asList(PaymentMethod.values());
    }

    public void setOrder(Order order) {
        synchronized (order) {
            order.setSecureID(UUID.randomUUID().toString());
            orderDao.save(order);
        }
    }

    private synchronized BigDecimal calculateDeliveryCost() {
        return new BigDecimal(5);
    }
}
