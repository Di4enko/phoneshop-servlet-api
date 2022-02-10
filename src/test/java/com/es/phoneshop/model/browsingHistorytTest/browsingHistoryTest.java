package com.es.phoneshop.model.browsingHistorytTest;

import com.es.phoneshop.model.browsingHistory.BrowsingHistory;
import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.util.Currency;

public class browsingHistoryTest {
    private BrowsingHistory browsingHistory;

    @Before
    public void setup() {
        browsingHistory = new BrowsingHistory();
    }

    @Test
    public void addNewTest() {
        Currency usd = Currency.getInstance("USD");
        Product test = new Product("test", "testProduct", new BigDecimal(320), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        browsingHistory.add(test);
        Product result = (browsingHistory.getRecentlyViewed())[0];
        int size = browsingHistory.getCurrentHistorySize();
        Assertions.assertEquals(test, result);
        Assertions.assertEquals(1, size);
    }

    @Test
    public void addSameTest() {
        Currency usd = Currency.getInstance("USD");
        Product test = new Product("test", "testProduct", new BigDecimal(320), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        browsingHistory.add(test);
        browsingHistory.add(test);
        int size = browsingHistory.getCurrentHistorySize();
        Assertions.assertEquals(1, size);
    }

    @Test
    public void addToFullHistory() {
        int maxSize = browsingHistory.getMaxHistorySize();
        Currency usd = Currency.getInstance("USD");
        for (int i = 1; i <= maxSize; i++) {
            String description = "testProduct" + i;
            Product product =new Product("test", description, new BigDecimal(320), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
            product.setId((long)i);
            browsingHistory.add(product);
        }
        int testSize = browsingHistory.getCurrentHistorySize();
        Product test4 = new Product("test", "testProduct4", new BigDecimal(320), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        browsingHistory.add(test4);
        int resultSize = browsingHistory.getCurrentHistorySize();
        boolean[] test = new boolean[maxSize];
        for (int i = 0; i < maxSize; i++) {
            test[i] = (browsingHistory.getRecentlyViewed())[i].getDescription().equals("testProduct" + (i + 2));
        }
        boolean[] result = new boolean[maxSize];
        for (int i = 0; i < maxSize; i++) {
            result[i] = true;
        }
        Assertions.assertEquals(testSize, resultSize);
        Assertions.assertArrayEquals(test, result);
        Assertions.assertEquals(resultSize, maxSize);
    }
}
