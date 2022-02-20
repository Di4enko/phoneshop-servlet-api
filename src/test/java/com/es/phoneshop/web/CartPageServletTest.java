package com.es.phoneshop.web;

import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.listener.DemoDataServletContextListener;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.service.cartService.CartService;
import com.es.phoneshop.service.cartService.CartServiceImp.CartServiceImpl;
import com.es.phoneshop.web.servlets.CartPageServlet;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartPageServletTest {
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpServletRequest request;
    @Mock
    private ServletConfig config;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private HttpSession session;
    @Mock
    private ServletContext servletContext;
    @Mock
    private ServletContextEvent servletContextEvent;
    private CartPageServlet servlet = new CartPageServlet();
    private CartService cartService = CartServiceImpl.getInstance();
    private DemoDataServletContextListener listener = new DemoDataServletContextListener();

    @Before
    public void setup() throws ServletException {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        when(request.getLocale()).thenReturn(Locale.UK);
        when(servletContextEvent.getServletContext()).thenReturn(servletContext);
        when(servletContext.getInitParameter(anyString())).thenReturn("true");
        listener.contextInitialized(servletContextEvent);
        servlet.init(config);
    }

    @Test
    public void doGetTest() throws ServletException, IOException {
        when(session.getAttribute(anyString())).thenReturn(null);
        servlet.doGet(request, response);
        verify(request).setAttribute(eq("cart"), any(Cart.class));
    }

    @Test
    public void doPostTest() throws OutOfStockException, ServletException, IOException {
        when(request.getParameterValues("quantity")).thenReturn(new String[]{"4", "2"});
        when(request.getParameterValues("productID")).thenReturn(new String[]{"1", "4"});
        Cart cart = cartService.getCart(request);
        when(session.getAttribute(anyString())).thenReturn(cart);
        cartService.add(cart, 1L, 1);
        cartService.add(cart, 4L, 1);
        servlet.doPost(request, response);
        int resultQuantity = cart.getTotalQuantity();
        Assertions.assertEquals(6, resultQuantity);
        verify(response).sendRedirect(anyString());
    }

    @Test
    public void doPostErrorTest() throws OutOfStockException, ServletException, IOException {
        when(request.getParameterValues("quantity")).thenReturn(new String[]{"4", "vsas"});
        when(request.getParameterValues("productID")).thenReturn(new String[]{"1", "4"});
        Cart cart = cartService.getCart(request);
        when(session.getAttribute(anyString())).thenReturn(cart);
        cartService.add(cart, 1L, 1);
        cartService.add(cart, 4L, 1);
        servlet.doPost(request, response);
        verify(request).setAttribute(eq("errors"), any(Map.class));
    }
}
