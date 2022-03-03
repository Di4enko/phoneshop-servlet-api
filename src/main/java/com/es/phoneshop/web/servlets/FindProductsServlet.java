package com.es.phoneshop.web.servlets;

import com.es.phoneshop.DAO.ProductDao;
import com.es.phoneshop.DAO.impl.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FindProductsServlet extends HttpServlet {
    private ProductDao productDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/parameterSearch.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> errors = new HashMap<>();
        List<Product> findProducts = new LinkedList<>();
        checkCode(request, errors);
        checkInput(request, errors, "minPrice");
        checkInput(request, errors, "maxPrice");
        checkInput(request, errors, "minStock");
        if(errors.isEmpty()) {
            findProducts = productDao.findProductsWithParameters(request.getParameter("code"), BigDecimal.valueOf(Integer.parseInt(request.getParameter("minPrice"))),
                    BigDecimal.valueOf(Integer.parseInt(request.getParameter("maxPrice"))), Integer.parseInt(request.getParameter("minStock")));
        }
        request.setAttribute("products", findProducts);
        request.setAttribute("errors", errors);
        doGet(request, response);
    }

    private void checkCode(HttpServletRequest request, Map<String, String> errors) {
        String parameter = request.getParameter("code");
        if (parameter == null || parameter.isEmpty()) {
            errors.put("code", "Incorrect input");
        }
    }

    private void checkInput(HttpServletRequest request, Map<String, String> errors, String requestParameter) {
        String parameter = request.getParameter(requestParameter);
        if (parameter == null || parameter.isEmpty()) {
            errors.put(requestParameter, "Incorrect input");
        } else {
            try {
                int param = Integer.parseInt(parameter);
                if(param < 0) {
                    errors.put(requestParameter, "Incorrect input");
                }
            } catch (NumberFormatException e) {
                errors.put(requestParameter, "Incorrect input");
            }
        }
    }
}
