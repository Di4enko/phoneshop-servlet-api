package com.es.phoneshop.model.cartTest;

import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.listener.DemoDataServletContextListener;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.service.cartService.CartService;
import com.es.phoneshop.service.cartService.CartServiceImp.CartServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceImplTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession httpSession;
    @Mock
    private Cart cart;
    @Mock
    private ServletContext servletContext;
    @Mock
    private ServletContextEvent servletContextEvent;
    private DemoDataServletContextListener listener = new DemoDataServletContextListener();
    private CartService cartService = CartServiceImpl.getInstance();

    @Before
    public void setup() {
        when(servletContextEvent.getServletContext()).thenReturn(servletContext);
        when(servletContext.getInitParameter(anyString())).thenReturn("true");
        when(request.getSession()).thenReturn(httpSession);
    }

    @Test
    public void getCartTest() {
        cartService.getCart(request);
        verify(request,times(2)).getSession();
    }

    @Test
    public void addTest() throws OutOfStockException {
        listener.contextInitialized(servletContextEvent);
        long productID = 3L;
        cart = cartService.getCart(request);
        cartService.add(cart, productID, 1);
        cartService.add(cart, productID, 2);
        int resultQuantity = cart.getByID(productID).getQuantity();
        Assertions.assertEquals(3, resultQuantity);
    }

    @Test(expected = OutOfStockException.class)
    public  void addExceptionTest() throws OutOfStockException {
        listener.contextInitialized(servletContextEvent);
        long productID = 3L;
        cart = cartService.getCart(request);
        cartService.add(cart, productID, Integer.MAX_VALUE);
    }
}
