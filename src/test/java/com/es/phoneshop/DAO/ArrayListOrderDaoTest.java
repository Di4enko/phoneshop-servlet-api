package com.es.phoneshop.DAO;

import com.es.phoneshop.DAO.impl.ArrayListOrderDao;
import com.es.phoneshop.enums.PaymentMethod;
import com.es.phoneshop.exception.OrderNotFoundException;
import com.es.phoneshop.model.order.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ArrayListOrderDaoTest {
    private OrderDao orderDao;
    private Order order;

    @Before
    public void setup() {
        orderDao = ArrayListOrderDao.getInstance();
        order = new Order();
        orderSetup(order);
    }

    @Test
    public void saveNewOrderTest() {
        orderDao.save(order);
        long result = 1;
        long test = order.getId();
        Assertions.assertEquals(result ,test);
    }

    @Test
    public void getOrderTest() {
        orderDao.save(order);
        Order testOrder = orderDao.getOrder(1L);
        Assertions.assertEquals(order, testOrder);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getOrderIllegalArgumentExceptionTest() {
        orderDao.getOrder(null);
    }

    @Test(expected = OrderNotFoundException.class)
    public void getOrderOrderNotFoundExceptionTest() {
        orderDao.save(order);
        orderDao.getOrder(2L);
    }

    @Test
    public void getOrderBySecureIDTest() {
        String secureID = order.getSecureID();
        orderDao.save(order);
        Order testOrder = orderDao.getOrderBySecureID(secureID);
        Assertions.assertEquals(order, testOrder);
    }

    private void orderSetup(Order order) {
        order.setFirstName("FirstName");
        order.setLastName("LastName");
        order.setPhone("+37529999999");
        order.setSecureID("secureID");
        order.setDeliveryCost(BigDecimal.valueOf(5L));
        order.setDeliveryDate(LocalDate.of(2000, 1, 1));
        order.setDeliveryAddress("Address");
        order.setPaymentMethod(PaymentMethod.CASH);
        order.setTotalQuantity(1);
        order.setSubtotal(BigDecimal.valueOf(5L));
        order.setTotalCost(BigDecimal.valueOf(100L));
    }
}
