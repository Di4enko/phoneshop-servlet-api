package com.es.phoneshop.model;

import com.es.phoneshop.DAO.ProductDao;
import com.es.phoneshop.DAO.impl.ArrayListProductDao;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.enums.SortField;
import com.es.phoneshop.enums.SortOrder;
import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import java.math.BigDecimal;
import java.util.*;

public class ArrayListProductDaoTest
{
    private ProductDao productDao;

    @Before
    public void setup() {

        productDao = ArrayListProductDao.getInstance();
        Currency usd = Currency.getInstance("USD");
        productDao.save(new Product("sgs", "Samsung Galaxy S", new BigDecimal(320), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        productDao.save(new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        productDao.save(new Product("sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        productDao.save(new Product("iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));
        productDao.save(new Product("iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg"));
        productDao.save(new Product("htces4g", "HTC EVO Shift 4G", new BigDecimal(100), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg"));
        productDao.save(new Product("sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg"));
    }

    @Test
    public void findProductsWithoutParametersTest() {
        Assertions.assertFalse(productDao.findProducts(null, null, null).isEmpty());
    }

    @Test
    public void findProductsWithQueryTest() {
        String query = "Apple 6";
        List<Product> testProducts = new LinkedList<>();
        testProducts.add(productDao.getProduct(5L));
        testProducts.add(productDao.getProduct(4L));
        productDao.getProduct(5L);
        List<Product> resultProducts = new LinkedList<>();
        resultProducts.add(productDao.findProducts(query,null,null).get(0));
        resultProducts.add(productDao.findProducts(query,null,null).get(1));
        Assertions.assertEquals(testProducts, resultProducts);
    }

    @Test
    public void findProductsWithSortDescriptionTest() {
        SortField field = SortField.DESCRIPTION;
        List<String> ascTest = new LinkedList<>();
        productDao.findProducts(null, null, null).forEach(e -> ascTest.add(e.getDescription()));
        ascTest.sort(String::compareTo);
        List<String> ascResult = new LinkedList<>();
        productDao.findProducts(null, field, SortOrder.ASC).forEach(e -> ascResult.add(e.getDescription()));
        List<String> descTest = new LinkedList<>(ascTest);
        descTest.sort(Comparator.reverseOrder());
        List<String> descResult = new LinkedList<>();
        productDao.findProducts(null, field, SortOrder.DESC).forEach(e -> descResult.add(e.getDescription()));
        Assertions.assertEquals(ascTest, ascResult);
        Assertions.assertEquals(descTest, descResult);
    }

    @Test
    public void findProductsWithSortPriceTest() {
        SortField field = SortField.PRICE;
        List<BigDecimal> ascTest = new LinkedList<>();
        productDao.findProducts(null, null, null).forEach(e -> ascTest.add(e.getPrice()));
        Collections.sort(ascTest);
        List<BigDecimal> ascResult = new LinkedList<>();
        productDao.findProducts(null, field, SortOrder.ASC).forEach(e -> ascResult.add(e.getPrice()));
        List<BigDecimal> descTest = new LinkedList<>(ascTest);
        descTest.sort(Comparator.reverseOrder());
        List<BigDecimal> descResult = new LinkedList<>();
        productDao.findProducts(null, field, SortOrder.DESC).forEach(e -> descResult.add(e.getPrice()));
        Assertions.assertEquals(ascTest, ascResult);
        Assertions.assertEquals(descTest, descResult);
    }

    @Test
    public void saveNewProductTest() throws ProductNotFoundException {
        Currency usd = Currency.getInstance("USD");
        Product testProduct = new Product("newTestProduct", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(testProduct);
        Product resultProduct = productDao.getProduct(testProduct.getId());
        Assertions.assertNotNull(resultProduct);
        Assertions.assertEquals(testProduct, resultProduct);
    }

    @Test
    public void saveProductWithIDTest() throws ProductNotFoundException {
        Currency usd = Currency.getInstance("USD");
        Product testProduct = new Product("newTestProduct", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        testProduct.setId(5L);
        Product previousProduct = productDao.getProduct(5L);
        productDao.save(testProduct);
        Product result = productDao.getProduct(5L);
        Assertions.assertEquals(testProduct, result);
        Assertions.assertNotEquals(previousProduct, result);
    }

    @Test
    public void getProductTest() throws ProductNotFoundException {
        Currency usd = Currency.getInstance("USD");
        Product testProduct = new Product("testProduct", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        testProduct.setId(5L);
        productDao.save(testProduct);
        Product result = productDao.getProduct(5L);
        Assertions.assertEquals(testProduct, result);
    }

    @Test(expected = NullPointerException.class)
    public void getProductNullPointerExceptionTest() {
        productDao.getProduct(null);
    }

    @Test(expected = ProductNotFoundException.class)
    public void getProductExceptionTest() throws ProductNotFoundException {
        productDao.getProduct(-1L);
    }

    @Test
    public void deleteTest() throws ProductNotFoundException {
        long testID = 5L;
        Product testProduct = productDao.getProduct(testID + 1);
        productDao.delete(testID);
        Product result = productDao.getProduct(testID);
        Assertions.assertEquals(testProduct, result);
    }

    @Test(expected = NullPointerException.class)
    public void deleteNullPointerExceptionTest() {
        productDao.getProduct(null);
    }

    @Test(expected = ProductNotFoundException.class)
    public void deleteExceptionTest() throws ProductNotFoundException {
        productDao.delete(-1L);
    }
}
