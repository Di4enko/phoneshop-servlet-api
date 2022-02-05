package com.es.phoneshop.model.browsingHistory;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;

public interface BrowsingHistoryService {
    void add(BrowsingHistory browsingHistory, Product product);
    BrowsingHistory getBrowsingHistory(HttpServletRequest request);
}
