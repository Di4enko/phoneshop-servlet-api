package com.es.phoneshop.web;

import com.es.phoneshop.DAO.impl.ArrayListProductDao;
import com.es.phoneshop.DAO.ProductDao;
import com.es.phoneshop.enums.SortField;
import com.es.phoneshop.enums.SortOrder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductListPageServlet extends HttpServlet {
    private ProductDao products;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        products = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        SortField sortField = request.getParameter("sort") != null ? SortField.valueOf(request.getParameter("sort")) : SortField.DEFAULT;
        SortOrder sortOrder = request.getParameter("order") != null ? SortOrder.valueOf(request.getParameter("order")) : SortOrder.DEFAULT;
        request.setAttribute("products", products.findProducts(query, sortField, sortOrder));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }

}
