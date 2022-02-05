package com.es.phoneshop.web;

import com.es.phoneshop.DAO.ProductDao;
import com.es.phoneshop.DAO.impl.ArrayListProductDao;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.browsingHistory.BrowsingHistoryService;
import com.es.phoneshop.model.browsingHistory.BrowsingHistoryServiceImpl;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.CartServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductDao productDao;
    private CartService cartService;
    private BrowsingHistoryService browsingHistory;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
        cartService = CartServiceImpl.getInstance();
        browsingHistory = BrowsingHistoryServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long productID = parseProductID(request);
        browsingHistory.add(browsingHistory.getBrowsingHistory(request),productDao.getProduct(productID));
        request.setAttribute("product", productDao.getProduct(productID));
        request.setAttribute("cart", cartService.getCart(request));
        request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        try {
            NumberFormat format = NumberFormat.getInstance(request.getLocale());
            int quantity = format.parse(request.getParameter("quantity")).intValue();
            long productID = parseProductID(request);
            cartService.add(cart, productID, quantity);
            productDao.getProduct(productID).setStock(productDao.getProduct(productID).getStock()-quantity);
            response.sendRedirect(request.getContextPath() + "/products/" + productID + "?success=Added to cart successfully" );
        } catch (OutOfStockException e)  {
            request.setAttribute("error", "Not enough stock");
            doGet(request,response);
        } catch (ParseException e) {
            request.setAttribute("error", "Not a number");
            doGet(request,response);
        }
    }

    private long parseProductID(HttpServletRequest request) {
        long productID = Long.parseLong(request.getPathInfo().substring(1));
        return productID;
    }
}
