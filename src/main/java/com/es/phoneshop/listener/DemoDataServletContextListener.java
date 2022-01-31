package com.es.phoneshop.listener;

import com.es.phoneshop.DAO.ProductDao;
import com.es.phoneshop.DAO.impl.ArrayListProductDao;
import com.es.phoneshop.model.PriceHistory;
import com.es.phoneshop.model.Product;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.math.BigDecimal;
import java.util.*;

public class DemoDataServletContextListener implements ServletContextListener {
    private ProductDao productDao;
    private Map<String, List<PriceHistory>> priceHistory;

    public DemoDataServletContextListener() {
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        boolean context = Boolean.parseBoolean(servletContextEvent.getServletContext().getInitParameter("insertDemoData"));
        if(context) {
            getSampleProducts();
            getHistory();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    private void getSampleProducts() {
        Currency usd = Currency.getInstance("USD");
        productDao.save(new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        productDao.save(new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        productDao.save(new Product("sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        productDao.save(new Product("iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));
        productDao.save(new Product("iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg"));
        productDao.save(new Product("htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg"));
        productDao.save(new Product("sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg"));
        productDao.save(new Product("xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg"));
        productDao.save(new Product("nokia3310", "Nokia 3310", new BigDecimal(70), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg"));
        productDao.save(new Product("palmp", "Palm Pixi", new BigDecimal(170), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg"));
        productDao.save(new Product("simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg"));
        productDao.save(new Product("simc61", "Siemens C61", new BigDecimal(80), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg"));
        productDao.save(new Product("simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg"));
    }

    private void getHistory() {
        initializationHistory();
        int size = priceHistory.size();
        for(int i=1; i <= size; i++) {
            productDao.getProduct((long) i).setPriceHistory(priceHistory.get(productDao.getProduct((long) i).getCode()));
        }
    }

    private void initializationHistory() {
        priceHistory = new HashMap<>();
        List<PriceHistory> sgs = Arrays.asList(new PriceHistory("15 Nov 2021", new BigDecimal(90)),
                new PriceHistory("15 Dec 2021",new BigDecimal(105) ), new PriceHistory("15 Jan 2022", new BigDecimal(98)));
        priceHistory.put("sgs", sgs);
        List<PriceHistory> sgs2 = Arrays.asList(new PriceHistory("15 Nov 2021", new BigDecimal(190)),
                new PriceHistory("15 Dec 2021", new BigDecimal(220)), new PriceHistory("15 Jan 2022", new BigDecimal(213)));
        priceHistory.put("sgs2", sgs2);
        List<PriceHistory> sgs3 = Arrays.asList(new PriceHistory("15 Nov 2021", new BigDecimal(286)),
                new PriceHistory("15 Dec 2021", new BigDecimal(344)), new PriceHistory("15 Jan 2022", new BigDecimal(315)));
        priceHistory.put("sgs3", sgs3);
        List<PriceHistory> iphone = Arrays.asList(new PriceHistory("15 Nov 2021", new BigDecimal(185)),
                new PriceHistory("15 Dec 2021", new BigDecimal(236)), new PriceHistory("15 Jan 2022", new BigDecimal(220)));
        priceHistory.put("iphone", iphone);
        List<PriceHistory> iphone6 = Arrays.asList(new PriceHistory("15 Nov 2021", new BigDecimal(980)),
                new PriceHistory("15 Dec 2021", new BigDecimal(960)), new PriceHistory("15 Jan 2022", new BigDecimal(1070)));
        priceHistory.put("iphone6", iphone6);
        List<PriceHistory> htces4g = Arrays.asList(new PriceHistory("15 Nov 2021", new BigDecimal(300)),
                new PriceHistory("15 Dec 2021", new BigDecimal(330)), new PriceHistory("15 Jan 2022", new BigDecimal(330)));
        priceHistory.put("htces4g", htces4g);
        List<PriceHistory> sec901 = Arrays.asList(new PriceHistory("15 Nov 2021", new BigDecimal(440)),
                new PriceHistory("15 Dec 2021", new BigDecimal(450)), new PriceHistory("15 Jan 2022", new BigDecimal(420)));
        priceHistory.put("sec901", sec901);
        List<PriceHistory> xperiaxz = Arrays.asList(new PriceHistory("15 Nov 2021", new BigDecimal(117)),
                new PriceHistory("15 Dec 2021", new BigDecimal(100)), new PriceHistory("15 Jan 2022", new BigDecimal(130)));
        priceHistory.put("xperiaxz", xperiaxz);
        List<PriceHistory> nokia3310 = Arrays.asList(new PriceHistory("15 Nov 2021", new BigDecimal(70)),
                new PriceHistory("15 Dec 2021", new BigDecimal(70)), new PriceHistory("15 Jan 2022", new BigDecimal(85)));
        priceHistory.put("nokia3310", nokia3310);
        List<PriceHistory> palmp = Arrays.asList(new PriceHistory("15 Nov 2021", new BigDecimal(185)),
                new PriceHistory("15 Dec 2021", new BigDecimal(200)), new PriceHistory("15 Jan 2022", new BigDecimal(150)));
        priceHistory.put("palmp", palmp);
        List<PriceHistory> simc56 = Arrays.asList(new PriceHistory("15 Nov 2021", new BigDecimal(80)),
                new PriceHistory("15 Dec 2021", new BigDecimal(90)), new PriceHistory("15 Jan 2022", new BigDecimal(100)));
        priceHistory.put("simc56", simc56);
        List<PriceHistory> simc61 = Arrays.asList(new PriceHistory("15 Nov 2021", new BigDecimal(80)),
                new PriceHistory("15 Dec 2021", new BigDecimal(90)), new PriceHistory("15 Jan 2022", new BigDecimal(750)));
        priceHistory.put("simc61", simc61);
        List<PriceHistory> simsxg75 = Arrays.asList(new PriceHistory("15 Nov 2021", new BigDecimal(160)),
                new PriceHistory("15 Dec 2021", new BigDecimal(145)), new PriceHistory("15 Jan 2022", new BigDecimal(160)));
        priceHistory.put("simsxg75", simsxg75);
    }
}
