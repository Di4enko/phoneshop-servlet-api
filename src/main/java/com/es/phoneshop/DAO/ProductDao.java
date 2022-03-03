package com.es.phoneshop.DAO;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.enums.SortField;
import com.es.phoneshop.enums.SortOrder;

import java.math.BigDecimal;
import java.util.List;

public interface ProductDao {
    void save(Product product);
    Product getProduct(Long id);
    List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder);
    List<Product> findProductsWithParameters(String code, BigDecimal minPrice, BigDecimal maxPrice, int minStock);
    void delete(Long id);
}
