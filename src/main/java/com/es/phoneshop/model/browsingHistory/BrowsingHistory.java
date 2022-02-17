package com.es.phoneshop.model.browsingHistory;

import com.es.phoneshop.model.product.Product;

import java.io.Serializable;

public class BrowsingHistory implements Serializable {
    private Product[] recentlyViewed;
    private int maxHistorySize = 3;
    private int currentHistorySize;
    private static  final long serialVersionUID = 1;

    public BrowsingHistory() {
        currentHistorySize = 0;
        recentlyViewed = new Product[maxHistorySize];
    }

    public Product[] getRecentlyViewed() {
        return recentlyViewed;
    }

    public void setRecentlyViewed(Product[] recentlyViewed) {
        this.recentlyViewed = recentlyViewed;
    }

    public int getMaxHistorySize() {
        return maxHistorySize;
    }

    public void setMaxHistorySize(int maxHistorySize) {
        this.maxHistorySize = maxHistorySize;
    }

    public int getCurrentHistorySize() {
        return currentHistorySize;
    }

    public void setCurrentHistorySize(int currentHistorySize) {
        this.currentHistorySize = currentHistorySize;
    }
}
