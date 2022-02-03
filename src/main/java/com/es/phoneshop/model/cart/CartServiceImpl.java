package com.es.phoneshop.model.cart;

import com.es.phoneshop.DAO.ProductDao;
import com.es.phoneshop.DAO.impl.ArrayListProductDao;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;

public class CartServiceImpl implements CartService{
    private static final String CART_SESSION_ATTRIBUTE = CartServiceImpl.class + "cart";
    private ProductDao productDao;
    private static  CartService instance;

    private CartServiceImpl() {
        productDao = ArrayListProductDao.getInstance();
    }

    public static CartService getInstance() {
        if(instance == null) {
            synchronized (CartServiceImpl.class) {
                if(instance == null) {
                    instance = new CartServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public Cart getCart(HttpServletRequest request) {
        Cart cart = (Cart) request.getSession().getAttribute(CART_SESSION_ATTRIBUTE);
        if(cart == null) {
            request.getSession().setAttribute(CART_SESSION_ATTRIBUTE, cart = new Cart());
        }
        return cart;
    }

    @Override
    public void add(Cart cart, Long productID, int quantity) throws OutOfStockException {
        if (quantity > productDao.getProduct(productID).getStock()) {
            throw new OutOfStockException(productDao.getProduct(productID), quantity);
        } else {
            CartItem item = cart.getByID(productID);
            if (item == null) {
                Product product = productDao.getProduct(productID);
                cart.getItems().add(new CartItem(product, quantity));
            } else {
                item.setQuantity(item.getQuantity() + quantity);
            }
        }
    }
}
