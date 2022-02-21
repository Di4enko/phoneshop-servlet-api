package com.es.phoneshop.web.servlets;

import com.es.phoneshop.DAO.ProductDao;
import com.es.phoneshop.DAO.impl.ArrayListProductDao;
import com.es.phoneshop.listener.DemoDataServletContextListener;
import com.es.phoneshop.web.servlets.ProductListPageServlet;
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
public class ProductListPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ServletConfig config;
    @Mock
    private HttpSession session;
    @Mock
    private ServletContext servletContext;
    @Mock
    private ServletContextEvent servletContextEvent;
    private ProductListPageServlet servlet = new ProductListPageServlet();
    private ProductDao productDao = ArrayListProductDao.getInstance();
    private DemoDataServletContextListener listener = new DemoDataServletContextListener();

    @Before
    public void setup() throws ServletException {
        when(servletContextEvent.getServletContext()).thenReturn(servletContext);
        when(servletContext.getInitParameter(anyString())).thenReturn("true");
        when(session.getAttribute(anyString())).thenReturn(null);
        when(request.getRequestURL()).thenReturn(new StringBuffer("Address"));
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        when(request.getLocale()).thenReturn(Locale.UK);
        when(request.getContextPath()).thenReturn("path");
        listener.contextInitialized(servletContextEvent);
        servlet.init(config);
    }

    @Test
    public void doGetTest() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(requestDispatcher).forward(request, response);
        verify(request).setAttribute(eq("products"), any());
        verify(request).setAttribute(eq("recentlyViewed"), any());
    }

    @Test
    public void doPostTest() throws ParseException, ServletException, IOException {
        when(request.getParameter("productID")).thenReturn("1");
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
    public void doPostErrorTest() throws IOException, ServletException, ParseException {
        when(request.getParameter("productID")).thenReturn("1");
        when(request.getParameter("quantity")).thenReturn("das");
        servlet.doPost(request, response);
        verify(request).setAttribute("error", "Not a number");
        verify(request).setAttribute(eq("errorID"), anyLong());
    }
}
