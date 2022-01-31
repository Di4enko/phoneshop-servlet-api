package com.es.phoneshop.DAO;

import com.es.phoneshop.model.Product;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.web.parameter.SortField;
import com.es.phoneshop.web.parameter.SortOrder;

import java.util.List;

public interface ProductDao {
    Product getProduct(Long id) throws ProductNotFoundException;
    List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder);
    void save(Product product);
    void delete(Long id) throws ProductNotFoundException;
}
