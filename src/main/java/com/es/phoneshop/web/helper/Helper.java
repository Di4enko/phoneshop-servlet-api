package com.es.phoneshop.web.helper;

import com.es.phoneshop.service.browsingHistoryService.BrowsingHistoryService;
import com.es.phoneshop.service.browsingHistoryService.browsingHistoryServiceImp.BrowsingHistoryServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.text.NumberFormat;
import java.text.ParseException;

public class Helper {
    private static Helper instance;

    private Helper() {}

    public static Helper getInstance() {
        if (instance == null) {
            synchronized (BrowsingHistoryService.class) {
                if (instance == null) {
                    instance = new Helper();
                }
            }
        }
        return instance;
    }
    public int parseQuantity(String quantityString, HttpServletRequest request) throws ParseException, NumberFormatException {
        synchronized (request.getSession()) {
            NumberFormat format = NumberFormat.getInstance(request.getLocale());
            Integer.parseInt(quantityString);
            return format.parse(quantityString).intValue();
        }
    }
}
