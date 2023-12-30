package com.cb.product.mapper;

import com.cb.product.entity.Product;
import com.cb.product.record.ProductRecord;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {

    ProductRecord productToProductRecord(Product product);

    List<ProductRecord> productsToProductRecords(List<Product> products);

    Product productRecordToProduct(ProductRecord productRecord);
}
