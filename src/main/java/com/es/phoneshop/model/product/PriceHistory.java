package com.es.phoneshop.model.product;

import java.io.Serializable;
import java.math.BigDecimal;

public class PriceHistory implements Serializable {
    private String date;
    private BigDecimal price;
    private static  final long serialVersionUID = 1;

    public PriceHistory(String date, BigDecimal price) {
        this.date = date;
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
