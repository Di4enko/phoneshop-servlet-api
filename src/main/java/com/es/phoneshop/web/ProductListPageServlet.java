package com.es.phoneshop.web;

import com.es.phoneshop.DAO.ProductDao;
import com.es.phoneshop.DAO.impl.ArrayListProductDao;
import com.es.phoneshop.enums.SortField;
import com.es.phoneshop.enums.SortOrder;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.service.browsingHistoryService.BrowsingHistoryService;
import com.es.phoneshop.service.browsingHistoryService.browsingHistoryServiceImp.BrowsingHistoryServiceImpl;
import com.es.phoneshop.service.cartService.CartService;
import com.es.phoneshop.service.cartService.CartServiceImp.CartServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;

public class ProductListPageServlet extends HttpServlet {
    private ProductDao products;
    private BrowsingHistoryService browsingHistoryService;
    private CartService cartService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        products = ArrayListProductDao.getInstance();
        browsingHistoryService = BrowsingHistoryServiceImpl.getInstance();
        cartService = CartServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        SortField sortField = request.getParameter("sort") != null ? SortField.valueOf(request.getParameter("sort")) : SortField.DEFAULT;
        SortOrder sortOrder = request.getParameter("order") != null ? SortOrder.valueOf(request.getParameter("order")) : SortOrder.DEFAULT;
        request.setAttribute("recentlyViewed", browsingHistoryService.getBrowsingHistory(request));
        request.setAttribute("products", products.findProducts(query, sortField, sortOrder));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Cart cart = cartService.getCart(request);
        long productID = Long.parseLong(request.getParameter("productID"));
        try {
            int quantity = parseQuantity(request);
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

    private int parseQuantity(HttpServletRequest request) throws ParseException, NumberFormatException {
        NumberFormat format = NumberFormat.getInstance(request.getLocale());
        Integer.parseInt(request.getParameter("quantity"));
        int quantity = format.parse(request.getParameter("quantity")).intValue();
        return quantity;
    }
}
