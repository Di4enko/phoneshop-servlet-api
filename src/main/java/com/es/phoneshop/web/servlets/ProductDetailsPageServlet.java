package com.es.phoneshop.web.servlets;

import com.es.phoneshop.DAO.ProductDao;
import com.es.phoneshop.DAO.impl.ArrayListProductDao;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.service.browsingHistoryService.BrowsingHistoryService;
import com.es.phoneshop.service.browsingHistoryService.browsingHistoryServiceImp.BrowsingHistoryServiceImpl;
import com.es.phoneshop.model.cart.Cart;
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

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductDao productDao;
    private CartService cartService;
    private BrowsingHistoryService browsingHistory;
    private MiniCartServlet miniCartServlet = new MiniCartServlet();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        miniCartServlet.init(config);
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
            int quantity = parseQuantity(request);
            long productID = parseProductID(request);
            cartService.add(cart, productID, quantity);
            response.sendRedirect(request.getContextPath() + "/products/" + productID + "?success=Added to cart successfully" );
        } catch (OutOfStockException e)  {
            request.setAttribute("error", "Not enough stock");
            doGet(request, response);
        } catch (ParseException | NumberFormatException e) {
            request.setAttribute("error", "Not a number");
            doGet(request, response);
        }
    }

    private long parseProductID(HttpServletRequest request) {
        long productID = Long.parseLong(request.getPathInfo().substring(1));
        return productID;
    }

    private int parseQuantity(HttpServletRequest request) throws ParseException, NumberFormatException {
        NumberFormat format = NumberFormat.getInstance(request.getLocale());
        Integer.parseInt(request.getParameter("quantity"));
        int quantity = format.parse(request.getParameter("quantity")).intValue();
        return quantity;
    }
}
