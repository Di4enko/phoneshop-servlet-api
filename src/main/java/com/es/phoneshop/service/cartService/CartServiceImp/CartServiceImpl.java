package com.es.phoneshop.service.cartService.CartServiceImp;

import com.es.phoneshop.DAO.ProductDao;
import com.es.phoneshop.DAO.impl.ArrayListProductDao;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.cartService.CartService;

import javax.servlet.http.HttpServletRequest;

public class CartServiceImpl implements CartService {
    private static final String CART_SESSION_ATTRIBUTE = CartServiceImpl.class + "cart";
    private ProductDao productDao;
    private static  CartService instance;

    private CartServiceImpl() {
        productDao = ArrayListProductDao.getInstance();
    }

    public static CartService getInstance() {
        if (instance == null) {
            synchronized (CartServiceImpl.class) {
                if (instance == null) {
                    instance = new CartServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public Cart getCart(HttpServletRequest request) {
        synchronized (request.getSession()) {
            Cart cart = (Cart) request.getSession().getAttribute(CART_SESSION_ATTRIBUTE);
            if (cart == null) {
                request.getSession().setAttribute(CART_SESSION_ATTRIBUTE, cart = new Cart());
            }
            return cart;
        }
    }

    @Override
    public synchronized void add(Cart cart, Long productID, int quantity) throws OutOfStockException {
        if (quantity > productDao.getProduct(productID).getStock()) {
            throw new OutOfStockException(productDao.getProduct(productID), quantity);
        } else {
            CartItem item = getFromCartByID(cart, productID);
            Product product = productDao.getProduct(productID);
            product.setStock(product.getStock() - quantity);
            if (item == null) {
                cart.getItems().add(new CartItem(product, quantity));
            } else {
                item.setQuantity(item.getQuantity() + quantity);
            }
        }
    }

    private CartItem getFromCartByID(Cart cart, long ID) {
        final CartItem[] item = {null};
        cart.getItems().forEach(e -> {if (e.getProduct().getId() == ID){item[0] = e;}});
        return item[0];
    }
}
