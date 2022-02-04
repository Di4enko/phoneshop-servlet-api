package com.es.phoneshop.model.browsingHistory;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;

public interface BrowsingHistoryService {
    void addProduct(BrowsingHistory browsingHistory, Product product);
    BrowsingHistory getBrowsingHistory(HttpServletRequest request);
}
