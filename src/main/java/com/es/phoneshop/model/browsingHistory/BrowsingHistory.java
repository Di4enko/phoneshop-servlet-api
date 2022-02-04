package com.es.phoneshop.model.browsingHistory;

import com.es.phoneshop.model.product.Product;

import java.util.Arrays;
import java.util.Objects;

public class BrowsingHistory {
    private Product[] browsingHistory;
    private int maxHistorySize = 3;
    private int currentHistorySize;

    public BrowsingHistory() {
        currentHistorySize = 0;
        browsingHistory = new Product[maxHistorySize];
    }

    public void add(Product product) {
        if (Arrays.stream(browsingHistory).filter(Objects::nonNull).noneMatch(e -> e.equals(product))) {
            if (currentHistorySize < maxHistorySize) {
                browsingHistory[currentHistorySize] = product;
                ++currentHistorySize;
            } else {
                for (int i = 0; i < maxHistorySize - 1; i++) {
                    browsingHistory[i] = browsingHistory[i + 1];
                }
                browsingHistory[currentHistorySize-1] = product;
            }
        }
    }

    public Product[] getBrowsingHistory() {
        return browsingHistory;
    }

    public void setBrowsingHistory(Product[] browsingHistory) {
        this.browsingHistory = browsingHistory;
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
