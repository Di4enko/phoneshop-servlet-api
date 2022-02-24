package com.es.phoneshop.web.servlets;

import com.es.phoneshop.DAO.ProductDao;
import com.es.phoneshop.DAO.impl.ArrayListProductDao;
import com.es.phoneshop.listener.DemoDataServletContextListener;
import com.es.phoneshop.web.servlets.ProductPriceHistoryServlet;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductPriceHistoryServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ServletContextEvent servletContextEvent;
    @Mock
    private ServletContext servletContext;
    @Mock
    private ServletConfig config;
    @Mock
    private HttpSession session;
    private DemoDataServletContextListener listener = new DemoDataServletContextListener();
    private ProductPriceHistoryServlet servlet = new ProductPriceHistoryServlet();
    private ProductDao productDao = ArrayListProductDao.getInstance();

    @Before
    public void setup() throws ServletException {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getPathInfo()).thenReturn("/1");
        when(servletContextEvent.getServletContext()).thenReturn(servletContext);
        when(servletContext.getInitParameter(anyString())).thenReturn("true");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(anyString())).thenReturn(null);
        listener.contextInitialized(servletContextEvent);
        servlet.init(config);
    }

    @Test
    public void doGetTest() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(request).setAttribute("product", productDao.getProduct(1L));
        verify(requestDispatcher).forward(request, response);
    }
}
