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
        verify(request,times(3)).getSession();
    }

    @Test
    public void addTest() throws OutOfStockException {
        listener.contextInitialized(servletContextEvent);
        long productID = 3L;
        cart = cartService.getCart(request);
        cartService.add(cart, productID, 1);
        cartService.add(cart, productID, 2);
        int resultQuantity = cart.getTotalQuantity();
        Assertions.assertEquals(3, resultQuantity);
    }

    @Test(expected = OutOfStockException.class)
    public  void addExceptionTest() throws OutOfStockException {
        listener.contextInitialized(servletContextEvent);
        long productID = 3L;
        cart = cartService.getCart(request);
        cartService.add(cart, productID, Integer.MAX_VALUE);
    }

    @Test
    public void updateTest() throws OutOfStockException {
        listener.contextInitialized(servletContextEvent);
        long productID = 1L;
        cart = cartService.getCart(request);
        cartService.add(cart, productID, 2);
        int testQuantity = 5;
        cartService.update(cart, productID, testQuantity);
        int resultQuantity = cart.getTotalQuantity();
        Assertions.assertEquals(testQuantity, resultQuantity);
    }

    @Test(expected = OutOfStockException.class)
    public void updateExceptionTest() throws OutOfStockException {
        listener.contextInitialized(servletContextEvent);
        long productID = 1L;
        cart = cartService.getCart(request);
        int quantity = 2;
        cartService.add(cart, productID, quantity);
        int testQuantity = (cart.getItems().get(0).getProduct().getStock() + quantity) + 1;
        cartService.update(cart, productID, testQuantity);

    }

    @Test
    public void deleteTest() throws OutOfStockException {
        listener.contextInitialized(servletContextEvent);
        long productID = 1L;
        cart = cartService.getCart(request);
        cartService.add(cart, productID, 2);
        cartService.delete(cart, productID);
        int resultQuantity = cart.getTotalQuantity();
        Assertions.assertEquals(0, resultQuantity);
    }
}
