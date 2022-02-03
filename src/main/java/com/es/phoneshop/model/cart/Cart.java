package com.es.phoneshop.model.cart;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public List<CartItem> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "Cart: " + items + '.';
    }

    public CartItem getByID(long id) {
        final CartItem[] item = {null};
        items.forEach(e -> {if(e.getProduct().getId() == id){item[0] = e;}});
        return item[0];
    }
}
