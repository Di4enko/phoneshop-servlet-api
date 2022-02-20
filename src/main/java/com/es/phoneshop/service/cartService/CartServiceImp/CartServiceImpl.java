package com.es.phoneshop.service.cartService.CartServiceImp;

import com.es.phoneshop.DAO.ProductDao;
import com.es.phoneshop.DAO.impl.ArrayListProductDao;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.cartService.CartService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public class CartServiceImpl implements CartService {
    private static final String CART_SESSION_ATTRIBUTE = CartServiceImpl.class + "cart";
    private ProductDao productDao;
    private static CartService instance;

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
    public void add(Cart cart, Long productID, int quantity) throws OutOfStockException {
        synchronized (cart) {
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
            recalculateCart(cart);
        }
    }

    @Override
    public void update(Cart cart, Long productID, int quantity) throws OutOfStockException {
        synchronized (cart) {
            CartItem item = getFromCartByID(cart, productID);
            Product product = productDao.getProduct(productID);
            int deltaQuantity = quantity - item.getQuantity();
            if (deltaQuantity > productDao.getProduct(productID).getStock()) {
                throw new OutOfStockException(productDao.getProduct(productID), quantity);
            } else {
                product.setStock(product.getStock() - deltaQuantity);
                item.setQuantity(quantity);
            }
            recalculateCart(cart);
        }
    }


    @Override
    public void delete(Cart cart, Long productID) {
        synchronized (cart) {
            CartItem item = getFromCartByID(cart, productID);
            Product product = productDao.getProduct(productID);
            product.setStock(product.getStock() + item.getQuantity());
            cart.getItems().removeIf(cartItem -> cartItem.getProduct().getId().equals(productID));
            recalculateCart(cart);
        }
    }

    @Override
    public void clear(Cart cart) {
        cart.getItems().clear();
        cart.setTotalCost(BigDecimal.valueOf(0));
        cart.setTotalQuantity(0);
    }

    private void recalculateCart(Cart cart) {
        cart.setTotalQuantity(cart.getItems().stream().map(CartItem::getQuantity).mapToInt(Integer::intValue).sum());
        final BigDecimal[] totalCost = {new BigDecimal(0)};
        cart.getItems().forEach(e -> {
            BigDecimal price = e.getProduct().getPrice();
            for(int i = 0; i < e.getQuantity(); i++) {
                totalCost[0] = totalCost[0].add(price);
            }
        });
        cart.setTotalCost(totalCost[0]);
    }

    private CartItem getFromCartByID (Cart cart,long ID){
        final CartItem[] item = {null};
        cart.getItems().forEach(e -> {
            if (e.getProduct().getId() == ID) {
                item[0] = e;
            }
        });
        return item[0];
    }
}
