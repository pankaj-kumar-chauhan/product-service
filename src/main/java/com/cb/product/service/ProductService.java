package com.cb.product.service;

import com.cb.product.record.ProductRecord;

import java.util.List;

public interface ProductService {

    ProductRecord save(ProductRecord productRecord);

    ProductRecord getProduct(String id);

    List<ProductRecord> getAllProducts();

    void deleteProduct(String id);
}
