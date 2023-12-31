package com.cb.product.service.impl;

import com.cb.product.entity.Product;
import com.cb.product.exception.RecordNotFoundException;
import com.cb.product.mapper.ProductMapper;
import com.cb.product.record.ProductRecord;
import com.cb.product.repository.ProductRepository;
import com.cb.product.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    final ProductRepository productRepository;

    final ProductMapper productMapper;

    @Override
    public ProductRecord save(ProductRecord productRecord) {
        Product product = productMapper.productRecordToProduct(productRecord);
        productRepository.save(product);
        return productMapper.productToProductRecord(product);
    }

    @Override
    public ProductRecord getProduct(String id) {
        return productRepository.findById(id)
                .map(productMapper::productToProductRecord)
                .orElseThrow(() -> new RecordNotFoundException("Product with ID [" + id + "] not found"));
    }

    @Override
    public List<ProductRecord> getAllProducts() {
        return productMapper.productsToProductRecords(productRepository.findAll());
    }

    @Override
    public void deleteProduct(String id) {
        Optional<Product> productOpt = productRepository.findById(id);
        productOpt.ifPresentOrElse((this::deleteProduct), () -> {
            throw new RecordNotFoundException("Product with ID [" + id + "] not found");
        });
    }

    private void deleteProduct(Product product) {
        product.setDeletedOn(LocalDateTime.now());
        product.setActive(false);
        productRepository.save(product);
    }
}
