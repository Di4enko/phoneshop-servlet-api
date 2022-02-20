package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

import java.io.Serializable;

public class CartItem implements Serializable, Cloneable {
    private Product product;
    private int quantity;
    private static  final long serialVersionUID = 1;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity =quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return product.getDescription() + "," + quantity;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
