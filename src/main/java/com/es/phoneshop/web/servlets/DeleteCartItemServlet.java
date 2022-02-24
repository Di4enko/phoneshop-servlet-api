package com.es.phoneshop.web.servlets;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.service.cartService.CartService;
import com.es.phoneshop.service.cartService.impl.CartServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteCartItemServlet extends HttpServlet {
    private CartService cartService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cartService = CartServiceImpl.getInstance();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cart cart = cartService.getCart(request);
        long productID = Long.parseLong(request.getPathInfo().substring(1));
        cartService.delete(cart, productID);
        response.sendRedirect(request.getContextPath() + "/cart?success=Product deleted successfully");
    }
}
