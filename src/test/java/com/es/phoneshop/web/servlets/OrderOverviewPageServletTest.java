package com.es.phoneshop.web.servlets;

import com.es.phoneshop.DAO.OrderDao;
import com.es.phoneshop.DAO.impl.ArrayListOrderDao;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.web.servlets.OrderOverviewPageServlet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderOverviewPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private ServletConfig config;
    @Mock
    private RequestDispatcher requestDispatcher;
    private OrderOverviewPageServlet servlet = new OrderOverviewPageServlet();
    private OrderDao orderDao = ArrayListOrderDao.getInstance();
    Order order = new Order();

    @Before
    public void setup() throws ServletException {
        servlet.init(config);
        order.setSecureID("1");
        orderDao.save(order);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getPathInfo()).thenReturn("/1");
    }

    @Test
    public void doGetTest() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(request).setAttribute("order", order);
        verify(requestDispatcher).forward(request, response);
    }
}
