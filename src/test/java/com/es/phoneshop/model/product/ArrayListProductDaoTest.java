package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import java.math.BigDecimal;
import java.util.Currency;

public class ArrayListProductDaoTest
{
    private ArrayListProductDao productDao;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
    }

    @Test
    public void FindProductsTest() {
        Assertions.assertFalse(productDao.findProducts().isEmpty());
    }

    @Nested
    public class SaveProductTest {
        private Product testProduct;

        @Before
        public void innerSetUp(){
            Currency usd = Currency.getInstance("USD");
            testProduct = new Product("newTestProduct", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        }

        @Test
        public void saveNewProductTest() throws ProductNotFoundException {
            productDao.save(testProduct);
            Product resultProduct = productDao.getProduct(testProduct.getId());
            Assertions.assertNotNull(resultProduct);
            Assertions.assertEquals(resultProduct, testProduct);
        }

        @Test
        public void saveProductWithIDTest() throws ProductNotFoundException {
            testProduct.setId(5L);
            Product previousProduct = productDao.getProduct(5L);
            productDao.save(testProduct);
            Product result = productDao.getProduct(5L);
            Assertions.assertEquals(result, testProduct);
            Assertions.assertNotEquals(result, previousProduct);
        }

        @Test
        public void saveProductWithNewID() throws ProductNotFoundException {
            testProduct.setId(productDao.getMaxID()*2);
            productDao.save(testProduct);
            long id = productDao.getMaxID() + 1L;
            Product result = productDao.getProduct(id);
            Assertions.assertEquals("newTestProduct", result.getCode());
        }
    }

    @Test
    public void getProductTest() throws ProductNotFoundException {
        Currency usd = Currency.getInstance("USD");
        Product testProduct=new Product("testProduct", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        testProduct.setId(5L);
        productDao.save(testProduct);
        Product result = productDao.getProduct(5L);
        Assertions.assertEquals(result, testProduct);
    }

    @Test(expected = ProductNotFoundException.class)
    public void getProductExceptionTest() throws ProductNotFoundException {
        productDao.getProduct(productDao.getMaxID()*2);

    }

    @Test
    public void deleteTest() throws ProductNotFoundException {
        long testID = 5L;
        Product testProduct= productDao.getProduct(testID+1);
        productDao.delete(testID);
        Product result = productDao.getProduct(testID);
        Assertions.assertEquals(result, testProduct);
    }

    @Test(expected = ProductNotFoundException.class)
    public void deleteExceptionTest() throws ProductNotFoundException {
        productDao.delete(productDao.getMaxID()*2);
    }
}
