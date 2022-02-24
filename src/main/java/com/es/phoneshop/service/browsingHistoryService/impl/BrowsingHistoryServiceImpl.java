package com.es.phoneshop.service.browsingHistoryService.impl;

import com.es.phoneshop.model.browsingHistory.BrowsingHistory;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.browsingHistoryService.BrowsingHistoryService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;

public class BrowsingHistoryServiceImpl implements BrowsingHistoryService {
    private static final String BROWSING_HISTORY_SESSION_ATTRIBUTE = BrowsingHistoryServiceImpl.class + "history";
    private static BrowsingHistoryService instance;

    private BrowsingHistoryServiceImpl() {}

    public static BrowsingHistoryService getInstance() {
        if (instance == null) {
            synchronized (BrowsingHistoryService.class) {
                if (instance == null) {
                    instance = new BrowsingHistoryServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public synchronized void add(BrowsingHistory browsingHistory, Product product) {
        if (Arrays.stream(browsingHistory.getRecentlyViewed()).filter(Objects::nonNull).noneMatch(e -> e.equals(product))) {
            Product[] recentlyViewed = browsingHistory.getRecentlyViewed();
            int maxHistorySize = browsingHistory.getMaxHistorySize();
            int currentHistorySize = browsingHistory.getCurrentHistorySize();
            if (currentHistorySize < maxHistorySize) {
                recentlyViewed[currentHistorySize] = product;
                browsingHistory.setCurrentHistorySize(currentHistorySize + 1);
            } else {
                for (int i = 0; i < maxHistorySize - 1; i++) {
                    recentlyViewed[i] = recentlyViewed[i + 1];
                }
                recentlyViewed[currentHistorySize-1] = product;
            }
        }
    }

    @Override
    public BrowsingHistory getBrowsingHistory(HttpServletRequest request) {
        synchronized (request.getSession()) {
            BrowsingHistory browsingHistory = (BrowsingHistory) request.getSession().getAttribute(BROWSING_HISTORY_SESSION_ATTRIBUTE);
            if (browsingHistory == null) {
                request.getSession().setAttribute(BROWSING_HISTORY_SESSION_ATTRIBUTE, browsingHistory = new BrowsingHistory());
            }
            return browsingHistory;
        }
    }
}
