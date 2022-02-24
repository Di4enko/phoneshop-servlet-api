package com.es.phoneshop.web.servlets;

import com.es.phoneshop.DAO.ProductDao;
import com.es.phoneshop.DAO.impl.ArrayListProductDao;
import com.es.phoneshop.enums.SortField;
import com.es.phoneshop.enums.SortOrder;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.service.browsingHistoryService.BrowsingHistoryService;
import com.es.phoneshop.service.browsingHistoryService.impl.BrowsingHistoryServiceImpl;
import com.es.phoneshop.service.cartService.CartService;
import com.es.phoneshop.service.cartService.impl.CartServiceImpl;
import com.es.phoneshop.helpers.ServletHelper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

public class ProductListPageServlet extends HttpServlet {
    private ProductDao products;
    private BrowsingHistoryService browsingHistoryService;
    private CartService cartService;
    private ServletHelper servletHelper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        products = ArrayListProductDao.getInstance();
        browsingHistoryService = BrowsingHistoryServiceImpl.getInstance();
        cartService = CartServiceImpl.getInstance();
        servletHelper = ServletHelper.getInstance();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        SortField sortField = request.getParameter("sort") != null ? SortField.valueOf(request.getParameter("sort")) : SortField.DEFAULT;
        SortOrder sortOrder = request.getParameter("order") != null ? SortOrder.valueOf(request.getParameter("order")) : SortOrder.DEFAULT;
        request.setAttribute("recentlyViewed", browsingHistoryService.getBrowsingHistory(request));
        request.setAttribute("products", products.findProducts(query, sortField, sortOrder));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Cart cart = cartService.getCart(request);
        long productID = Long.parseLong(request.getParameter("productID"));
        try {
            int quantity = servletHelper.parseQuantity(request.getParameter("quantity"), request);
            cartService.add(cart, productID, quantity);
            response.sendRedirect(request.getRequestURL() + "?success=Product added to cart successfully");
        } catch (ParseException | NumberFormatException e) {
            request.setAttribute("error", "Not a number");
            request.setAttribute("errorID", productID);
            doGet(request, response);
        } catch (OutOfStockException e) {
            request.setAttribute("error", "Not enough stock");
            request.setAttribute("errorID", productID);
            doGet(request, response);
        }
    }
}
