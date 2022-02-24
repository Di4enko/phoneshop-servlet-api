package com.es.phoneshop.web.servlets;

import com.es.phoneshop.DAO.OrderDao;
import com.es.phoneshop.DAO.impl.ArrayListOrderDao;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderOverviewPageServlet extends HttpServlet {
    private OrderDao orderDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        orderDao = ArrayListOrderDao.getInstance();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String secureID = request.getPathInfo().substring(1);
        request.setAttribute("order", orderDao.getOrderBySecureID(secureID));
        request.getRequestDispatcher("/WEB-INF/pages/overview.jsp").forward(request, response);
    }
}
