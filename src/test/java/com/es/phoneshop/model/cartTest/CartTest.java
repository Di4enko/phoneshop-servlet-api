package com.es.phoneshop.model.cartTest;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.util.Currency;

public class CartTest {
    private Cart cart;

    @Before
    public void setup() {
        cart = new Cart();
    }

    @Test
    public void getByIDTest() {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test", "testProduct", new BigDecimal(320), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        product.setId(4L);
        CartItem test = new CartItem(product, 3);
        cart.getItems().add(test);
        CartItem result = cart.getByID(4);
        Assertions.assertEquals(test, result);
    }
}
