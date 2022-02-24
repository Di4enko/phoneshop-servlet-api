package com.es.phoneshop.web.servlets;

import com.es.phoneshop.DAO.ProductDao;
import com.es.phoneshop.DAO.impl.ArrayListProductDao;
import com.es.phoneshop.listener.DemoDataServletContextListener;
import com.es.phoneshop.web.servlets.ProductDetailsPageServlet;
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
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private ServletConfig config;
    @Mock
    private HttpSession session;
    @Mock
    private ServletContext servletContext;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ServletContextEvent servletContextEvent;
    private DemoDataServletContextListener listener = new DemoDataServletContextListener();
    private ProductDetailsPageServlet servlet = new ProductDetailsPageServlet();
    private ProductDao productDao = ArrayListProductDao.getInstance();

    @Before
    public void setup() throws ServletException {
        when(servletContextEvent.getServletContext()).thenReturn(servletContext);
        when(servletContext.getInitParameter(anyString())).thenReturn("true");
        when(session.getAttribute(anyString())).thenReturn(null);
        when(request.getPathInfo()).thenReturn("11");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        when(request.getLocale()).thenReturn(Locale.UK);
        when(request.getContextPath()).thenReturn("path");
        listener.contextInitialized(servletContextEvent);
        servlet.init(config);
    }

    @Test
    public void doGetTest() throws ServletException, IOException {
        listener.contextInitialized(servletContextEvent);
        servlet.doGet(request, response);
        verify(request).setAttribute(eq("product"), any());
        verify(request).setAttribute(eq("cart"), any());
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void doPostTest() throws ServletException, IOException, ParseException {
        when(request.getParameter("quantity")).thenReturn("1");
        NumberFormat format = NumberFormat.getInstance(request.getLocale());
        int quantity = format.parse(request.getParameter("quantity")).intValue();
        int testStock = productDao.getProduct(1L).getStock() - quantity;
        servlet.doPost(request, response);
        int resultStock = productDao.getProduct(1L).getStock();
        verify(response).sendRedirect(anyString());
        Assertions.assertEquals(testStock, resultStock);
    }

    @Test
    public void doPostOutOfStockExceptionTest() throws ServletException, IOException {
        when(request.getParameter("quantity")).thenReturn("1000000");
        servlet.doPost(request, response);
        verify(request).setAttribute(eq("error"), eq("Not enough stock"));
        verify(request).setAttribute(eq("product"), any());
        verify(request).setAttribute(eq("cart"), any());
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void doPostParseExceptionTest() throws ServletException, IOException {
        when(request.getParameter("quantity")).thenReturn("notNumber");
        servlet.doPost(request, response);
        verify(request).setAttribute(eq("error"), eq("Not a number"));
        verify(request).setAttribute(eq("product"), any());
        verify(request).setAttribute(eq("cart"), any());
        verify(requestDispatcher).forward(request, response);
    }
}
