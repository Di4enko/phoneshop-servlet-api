package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;
import java.math.BigDecimal;
import java.util.Currency;
import static org.junit.Assert.*;

public class ArrayListProductDaoTest
{
    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
    }

    @Test
    public void FindProductsNoResultsTest() {
        assertFalse(productDao.findProducts().isEmpty());
    }

    @Test
    public void FindProductsTest(){
        assertTrue((long) productDao.findProducts().size() >0);
    }

    @Test
    public void saveNewProductTest() throws ProductNotFoundException {
        Currency usd = Currency.getInstance("USD");
        Product testProduct=new Product("newTestProduct", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(testProduct);
        Product resultProduct = productDao.getProduct(testProduct.getId());
        assertNotNull(resultProduct);
        assertEquals("newTestProduct",resultProduct.getCode());
    }

    @Test
    public void saveProductWithIDTest() throws ProductNotFoundException {
        Currency usd = Currency.getInstance("USD");
        Product testProduct=new Product("productWithID", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        testProduct.setId(5L);
        Product previousProduct = productDao.getProduct(5L);
        productDao.save(testProduct);
        Product result = productDao.getProduct(5L);
        assertEquals("productWithID", result.getCode());
        assertNotEquals(previousProduct.getCode(), result.getCode());
    }

    @Test
    public void saveProductWithNewID() throws ProductNotFoundException {
        Currency usd = Currency.getInstance("USD");
        Product testProduct=new Product("productWithID", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        testProduct.setId(20L);
        productDao.save(testProduct);
        long id=productDao.findProducts().stream().count()+1;
        Product result = productDao.getProduct(id);
        assertEquals("productWithID", result.getCode());
    }

    @Test
    public void getProductTest() throws ProductNotFoundException {
        Currency usd = Currency.getInstance("USD");
        Product testProduct=new Product("testProduct", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        testProduct.setId(5L);
        productDao.save(testProduct);
        Product result = productDao.getProduct(5L);
        assertEquals(result, testProduct);
    }

    @Test
    public void deleteTest() throws ProductNotFoundException {
        long testID = 5L;
        Product testProduct= productDao.getProduct(testID+1);
        productDao.delete(testID);
        Product result = productDao.getProduct(testID);
        assertEquals(result.getCode(), testProduct.getCode());
    }
}
