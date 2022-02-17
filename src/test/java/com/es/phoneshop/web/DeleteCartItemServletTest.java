package com.es.phoneshop.web;

import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.listener.DemoDataServletContextListener;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.service.cartService.CartService;
import com.es.phoneshop.service.cartService.CartServiceImp.CartServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeleteCartItemServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
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
    private DeleteCartItemServlet servlet = new DeleteCartItemServlet();
    private DemoDataServletContextListener listener = new DemoDataServletContextListener();
    private CartService cartService = CartServiceImpl.getInstance();

    @Before
    public void setup() throws ServletException {
        servlet.init(config);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        when(request.getPathInfo()).thenReturn("/1");
        when(request.getLocale()).thenReturn(Locale.UK);
        when(servletContextEvent.getServletContext()).thenReturn(servletContext);
        when(servletContext.getInitParameter(anyString())).thenReturn("true");
        listener.contextInitialized(servletContextEvent);
    }

    @Test
    public void doPostTest() throws IOException, OutOfStockException {
        Cart cart = cartService.getCart(request);
        cartService.add(cart, 1L, 1);
        when(session.getAttribute(anyString())).thenReturn(cart);
        servlet.doPost(request, response);
        verify(response).sendRedirect(anyString());
    }
}
