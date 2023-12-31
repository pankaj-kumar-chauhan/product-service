package com.cb.product.controller;

import com.cb.product.record.ProductRecord;
import com.cb.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {

    final ProductService productService;

    @PostMapping
    ResponseEntity<ProductRecord> save(@RequestBody @Valid ProductRecord productRecord) {
        ProductRecord productRecordChanged = productService.save(productRecord);
        return new ResponseEntity<>(productRecordChanged, productRecord.id() != null ? HttpStatus.OK : HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    ResponseEntity<ProductRecord> getProduct(@PathVariable String id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @GetMapping
    ResponseEntity<List<ProductRecord>> getAllProduct() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product Deleted with id" + id);
    }

}
