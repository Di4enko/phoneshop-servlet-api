package com.es.phoneshop.web.servlets;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.service.cartService.CartService;
import com.es.phoneshop.service.cartService.impl.CartServiceImpl;
import com.es.phoneshop.service.orderService.OrderService;
import com.es.phoneshop.service.orderService.impl.OrderServiceImpl;
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
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CheckoutPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private ServletConfig config;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher requestDispatcher;
    private CheckoutPageServlet servlet = new CheckoutPageServlet();
    private CartService cartService = CartServiceImpl.getInstance();
    private OrderService orderService = OrderServiceImpl.getInstance();
    private Cart cart = new Cart();

    @Before
    public void setup() throws ServletException {
        servlet.init(config);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        cartSetup(cart);
        when(session.getAttribute(anyString())).thenReturn(cart);
        when(request.getParameter(anyString())).thenReturn("parameter");
    }

    @Test
    public void doGetTest() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(request).setAttribute(eq("order"), any(Order.class));
        verify(request).setAttribute(eq("paymentMethod"), any(List.class));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void doPostTest() throws ServletException, IOException {
        when(request.getParameter("paymentMethod")).thenReturn("CASH");
        when(request.getParameter("phone")).thenReturn("1234567891234");
        when(request.getParameter("deliveryDate")).thenReturn("12.12.2022");
        servlet.doPost(request, response);
        verify(response).sendRedirect(anyString());
    }

    @Test
    public void doPostErrorTest() throws ServletException, IOException {
        when(request.getParameter("paymentMethod")).thenReturn("");
        when(request.getParameter("phone")).thenReturn("phone");
        when(request.getParameter("deliveryDate")).thenReturn("date");
        servlet.doPost(request, response);
        verify(request).setAttribute(eq("errors"), any(Map.class));
    }

    private void cartSetup(Cart cart) {
        cart.setTotalQuantity(1);
        cart.setTotalCost(BigDecimal.valueOf(100L));
        List<CartItem> items= new LinkedList<>();
        items.add(new CartItem(null, 1));
        cart.setItems(items);
    }
}
