package com.cb.product.service.impl;

import com.cb.product.entity.Product;
import com.cb.product.exception.RecordNotFoundException;
import com.cb.product.mapper.ProductMapperImpl;
import com.cb.product.record.ProductRecord;
import com.cb.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @Spy
    ProductMapperImpl productMapper;

    @InjectMocks
    ProductServiceImpl productService;

    Product product;
    ProductRecord productRecord;

    @BeforeEach
    void setup() {
        product = new Product("testID", "testName", new BigDecimal("20.30"),
                "testShortDesc", "testLongDesc", "", "",
                null, null, null, true);

        productRecord = new ProductRecord(null, "testName", new BigDecimal("20.30"),
                "testShortDesc", "testLongDesc", "", "",
                null, null, null, true);
    }

    @Test
    void saveTest() {
        Product productToSave = new Product(null, "testName", new BigDecimal("20.30"),
                "testShortDesc", "testLongDesc", "", "",
                null, null, null, true);
        when(productRepository.save(productToSave)).thenReturn(product);
        ProductRecord productRecordSaved = productService.save(productRecord);

        assertEquals("testID", productRecordSaved.id());
        assertEquals("testName", productRecordSaved.name());
        assertEquals(new BigDecimal("20.30"), productRecordSaved.price());
        assertEquals("testShortDesc", productRecordSaved.shortDesc());
        assertEquals("testLongDesc", productRecordSaved.longDesc());
        assertTrue(productRecordSaved.active());
        verify(productRepository, times(1)).save(productToSave);
        verify(productMapper, times(1)).productRecordToProduct(productRecord);
        verify(productMapper, times(1)).productToProductRecord(product);
    }

    @Test
    void getProductTest(){
        when(productRepository.findById("testID")).thenReturn(Optional.of(product));
        ProductRecord productRecordFound = productService.getProduct("testID");
        assertEquals("testID", productRecordFound.id());
        assertEquals("testName", productRecordFound.name());
        assertEquals(new BigDecimal("20.30"), productRecordFound.price());
        assertEquals("testShortDesc", productRecordFound.shortDesc());
        assertEquals("testLongDesc", productRecordFound.longDesc());
        assertTrue(productRecordFound.active());
        verify(productRepository, times(1)).findById("testID");
        verify(productMapper, times(1)).productToProductRecord(product);
    }

    @Test
    void getProductRecordNotFoundExceptionTest() {
        when(productRepository.findById("testID")).thenReturn(Optional.empty());
        Exception exception = assertThrows(RecordNotFoundException.class, () -> productService.getProduct("testID"));
        assertEquals("Product with ID [testID] not found", exception.getMessage());
        verify(productRepository, times(1)).findById("testID");
        verify(productMapper, times(0)).productToProductRecord(product);
    }

    @Test
    void getAllProductsTest(){
       Product product2 = new Product("testID2", "testName2", new BigDecimal("30.30"),
                "testShortDesc2", "testLongDesc2", "", "",
                null, null, null, false);
        when(productRepository.findAll()).thenReturn(Arrays.asList(product,product2));
        List<ProductRecord> allProducts = productService.getAllProducts();

        assertEquals(2,allProducts.size());

        ProductRecord productRecordFound = allProducts.get(0);
        assertEquals("testID", productRecordFound.id());
        assertEquals("testName", productRecordFound.name());
        assertEquals(new BigDecimal("20.30"), productRecordFound.price());
        assertEquals("testShortDesc", productRecordFound.shortDesc());
        assertEquals("testLongDesc", productRecordFound.longDesc());
        assertTrue(productRecordFound.active());

        productRecordFound = allProducts.get(1);
        assertEquals("testID2", productRecordFound.id());
        assertEquals("testName2", productRecordFound.name());
        assertEquals(new BigDecimal("30.30"), productRecordFound.price());
        assertEquals("testShortDesc2", productRecordFound.shortDesc());
        assertEquals("testLongDesc2", productRecordFound.longDesc());
        assertFalse(productRecordFound.active());
    }

    @Test
    void deleteProductTest(){
        when(productRepository.findById("testID")).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);
        productService.deleteProduct("testID");

        assertEquals("testID", product.getId());
        assertEquals("testName", product.getName());
        assertEquals(new BigDecimal("20.30"), product.getPrice());
        assertEquals("testShortDesc", product.getShortDesc());
        assertEquals("testLongDesc", product.getLongDesc());
        assertNotNull(product.getDeletedOn());
        assertFalse(product.getActive());
    }

    @Test
    void deleteProductRecordNotFoundExceptionTest() {
        when(productRepository.findById("testID")).thenReturn(Optional.empty());
        Exception exception = assertThrows(RecordNotFoundException.class, () -> productService.deleteProduct("testID"));
        assertEquals("Product with ID [testID] not found", exception.getMessage());
        verify(productRepository, times(1)).findById("testID");
        verify(productRepository, times(0)).save(product);
    }
}
