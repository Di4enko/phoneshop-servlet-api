package com.es.phoneshop.web;

import com.es.phoneshop.exception.OutOfStockException;
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
import java.util.HashMap;
import java.util.Map;

public class CartPageServlet extends HttpServlet {
    private CartService cartService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cartService = CartServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("cart", cartService.getCart(request));
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] quantities = request.getParameterValues("quantity");
        String[] productIDs = request.getParameterValues("productID");
        Map<Long, String> errors = new HashMap<>();
        for (int i = 0; i < quantities.length; i++) {
            try {
                int quantity = parseQuantity(quantities[i], request);
                long productID = Long.parseLong(productIDs[i]);
                cartService.update(cartService.getCart(request), productID, quantity);
            } catch (ParseException | NumberFormatException e) {
                 errors.put(Long.parseLong(productIDs[i]), "Not a number");
            } catch (OutOfStockException e) {
                errors.put(Long.parseLong(productIDs[i]), "Not enough stock");
            }
        }
        if(errors.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart?success=Cart updated successfully");
        } else {
            request.setAttribute("errors", errors);
            doGet(request, response);
        }
    }

    private int parseQuantity(String quantityString, HttpServletRequest request) throws ParseException, NumberFormatException {
        NumberFormat format = NumberFormat.getInstance(request.getLocale());
        Integer.parseInt(quantityString);
        return format.parse(quantityString).intValue();
    }
}