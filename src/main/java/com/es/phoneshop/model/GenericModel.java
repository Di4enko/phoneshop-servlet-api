package com.es.phoneshop.model;

import com.es.phoneshop.model.product.Product;

import java.util.Currency;

public class GenericModel {
    private Long id;
    private Currency currency;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.getId() && currency.equals(product.getCurrency());
    }
}
