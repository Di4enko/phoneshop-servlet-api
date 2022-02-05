package com.es.phoneshop.model.browsingHistory;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;

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
    //TODO
    @Override
    public synchronized void add(BrowsingHistory browsingHistory, Product product) {
        browsingHistory.add(product);
    }
    //TODO
    @Override
    public synchronized BrowsingHistory getBrowsingHistory(HttpServletRequest request) {
        BrowsingHistory browsingHistory = (BrowsingHistory) request.getSession().getAttribute(BROWSING_HISTORY_SESSION_ATTRIBUTE);
        if(browsingHistory == null) {
            request.getSession().setAttribute(BROWSING_HISTORY_SESSION_ATTRIBUTE, browsingHistory = new BrowsingHistory());
        }
        return browsingHistory;
    }
}
