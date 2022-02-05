package com.es.phoneshop.model.browsingHistory;

import com.es.phoneshop.model.product.Product;

import java.util.Arrays;
import java.util.Objects;

public class BrowsingHistory {
    private Product[] recentlyViewed;
    private int maxHistorySize = 3;
    private int currentHistorySize;

    public BrowsingHistory() {
        currentHistorySize = 0;
        recentlyViewed = new Product[maxHistorySize];
    }

    public void add(Product product) {
        if (Arrays.stream(recentlyViewed).filter(Objects::nonNull).noneMatch(e -> e.equals(product))) {
            if (currentHistorySize < maxHistorySize) {
                recentlyViewed[currentHistorySize] = product;
                ++currentHistorySize;
            } else {
                for (int i = 0; i < maxHistorySize - 1; i++) {
                    recentlyViewed[i] = recentlyViewed[i + 1];
                }
                recentlyViewed[currentHistorySize-1] = product;
            }
        }
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
