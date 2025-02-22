package com.es.phoneshop.web.servlets;

import com.es.phoneshop.enums.PaymentMethod;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.service.cartService.CartService;
import com.es.phoneshop.service.cartService.impl.CartServiceImpl;
import com.es.phoneshop.service.orderService.OrderService;
import com.es.phoneshop.service.orderService.impl.OrderServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CheckoutPageServlet extends HttpServlet {
    private CartService cartService;
    private OrderService orderService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cartService = CartServiceImpl.getInstance();
        orderService = OrderServiceImpl.getInstance();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        request.setAttribute("order", orderService.getOrder(cart));
        request.setAttribute("paymentMethod", orderService.getPaymentMethod());
        request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        Order order = orderService.getOrder(cart);
        Map<String, String> errors = new HashMap<>();
        setRequiredParameters(request, errors, "firstName", order::setFirstName);
        setRequiredParameters(request, errors, "lastName", order::setLastName);
        setRequiredParameters(request, errors, "deliveryAddress", order::setDeliveryAddress);
        setPhone(request, errors ,order);
        setDeliveryDate(request, errors, order);
        setPaymentMethode(request, errors, order);
        if (errors.isEmpty()) {
            orderService.setOrder(order);
            cartService.clear(cart);
            response.sendRedirect(request.getContextPath() + "/order/overview/" + order.getSecureID());
        } else {
            request.setAttribute("errors", errors);
            doGet(request, response);
        }
    }

    private void setRequiredParameters(HttpServletRequest request, Map<String, String> errors, String requestParameter, Consumer<String> consumer) {
        String parameter = request.getParameter(requestParameter);
        if (parameter == null || parameter.isEmpty()) {
            errors.put(requestParameter, "Incorrect input");
        } else {
            consumer.accept(parameter);
        }
    }

    private void setPhone(HttpServletRequest request, Map<String, String> errors, Order order) {
        String parameter = request.getParameter("phone");

        if (parameter == null || parameter.isEmpty()) {
            errors.put("phone", "Phone not input");
        } else {
            try {
                if (parameter.length() == 13) {
                    Long.valueOf(parameter.substring(1, 12));
                    order.setPhone(parameter);
                } else {
                    errors.put("phone", "Incorrect input");
                }
            } catch (NumberFormatException e) {
                errors.put("phone", "Incorrect input");
            }
        }
    }

    private void setPaymentMethode(HttpServletRequest request, Map<String, String> errors, Order order) {
        String parameter = request.getParameter("paymentMethod");
        if (parameter == null || parameter.isEmpty()) {
            errors.put("paymentMethod", "No payment method selected");
        } else {
            order.setPaymentMethod(PaymentMethod.valueOf(parameter));
        }
    }

    private void setDeliveryDate(HttpServletRequest request, Map<String, String> errors, Order order) {
        try {
            LocalDate date = parseDate(request);
            order.setDeliveryDate(date);
        } catch (NumberFormatException | DateTimeException e) {
            errors.put("deliveryDate", "Incorrect input");
        }
    }

    private LocalDate parseDate(HttpServletRequest request) throws NumberFormatException, DateTimeException{
            int year = Integer.parseInt(request.getParameter("year"));
            int month = Integer.parseInt(request.getParameter("month"));
            int day = Integer.parseInt(request.getParameter("day"));
            return LocalDate.of(year, month, day);
    }
}