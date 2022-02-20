package com.es.phoneshop.helpers;

import com.es.phoneshop.service.browsingHistoryService.BrowsingHistoryService;

import javax.servlet.http.HttpServletRequest;
import java.text.NumberFormat;
import java.text.ParseException;

public class ServletHelper {
    private static ServletHelper instance;

    private ServletHelper() {}

    public static ServletHelper getInstance() {
        if (instance == null) {
            synchronized (BrowsingHistoryService.class) {
                if (instance == null) {
                    instance = new ServletHelper();
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
