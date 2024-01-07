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

    public static final String LONG_DESC_1 = """
            About this item
            15.40 cm (6.1-inch) Super Retina XDR display
            Advanced camera system for better photos in any light
            Cinematic mode now in 4K Dolby Vision up to 30 fps
            Action mode for smooth, steady, handheld videos
            Vital safety technology — Crash Detection calls for help when you can’t
            All-day battery life and up to 20 hours of video playback
            Industry-leading durability features with Ceramic Shield and water resistance
            A15 Bionic chip with 5-core GPU for lightning-fast performance. Superfast 5G cellular
            iOS 16 offers even more ways to personalise, communicate and share
            """;
    public static final String LONG_DESC_2 = """
            About this item
            15 cm (6.1-inch) Super Retina XDR display
            Cinematic mode adds shallow depth of field and shifts focus automatically in your videos
            Advanced dual-camera system with 12MP Wide and Ultra Wide cameras; Photographic Styles, Smart HDR 4, Night mode, 4K Dolby Vision HDR recording
            12MP TrueDepth front camera with Night mode, 4K Dolby Vision HDR recording
            A15 Bionic chip for lightning-fast performance
            """;
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
        product = new Product("6590722a46eb225aac1cfd22", "iPhone 14", new BigDecimal("58999"),
                "iPhone 14 (128 GB) - Midnight", LONG_DESC_1, "", "",
                null, null, null, true);

        productRecord = new ProductRecord(null, "iPhone 14", new BigDecimal("58999"),
                "iPhone 14 (128 GB) - Midnight", LONG_DESC_1, "", "",
                null, null, null, true);
    }

    @Test
    void saveTest() {
        Product productToSave = new Product(null, "iPhone 14", new BigDecimal("58999"),
                "iPhone 14 (128 GB) - Midnight", LONG_DESC_1, "", "",
                null, null, null, true);
        when(productRepository.save(productToSave)).thenReturn(product);
        ProductRecord productRecordSaved = productService.save(productRecord);

        assertEquals("6590722a46eb225aac1cfd22", productRecordSaved.id());
        assertEquals("iPhone 14", productRecordSaved.name());
        assertEquals(new BigDecimal("58999"), productRecordSaved.price());
        assertEquals("iPhone 14 (128 GB) - Midnight", productRecordSaved.shortDesc());
        assertEquals(LONG_DESC_1, productRecordSaved.longDesc());
        assertTrue(productRecordSaved.active());
        verify(productRepository, times(1)).save(productToSave);
        verify(productMapper, times(1)).productRecordToProduct(productRecord);
        verify(productMapper, times(1)).productToProductRecord(product);
    }

    @Test
    void getProductTest(){
        when(productRepository.findById("6590722a46eb225aac1cfd22")).thenReturn(Optional.of(product));
        ProductRecord productRecordFound = productService.getProduct("6590722a46eb225aac1cfd22");
        assertEquals("6590722a46eb225aac1cfd22", productRecordFound.id());
        assertEquals("iPhone 14", productRecordFound.name());
        assertEquals(new BigDecimal("58999"), productRecordFound.price());
        assertEquals("iPhone 14 (128 GB) - Midnight", productRecordFound.shortDesc());
        assertEquals(LONG_DESC_1, productRecordFound.longDesc());
        assertTrue(productRecordFound.active());
        verify(productRepository, times(1)).findById("6590722a46eb225aac1cfd22");
        verify(productMapper, times(1)).productToProductRecord(product);
    }

    @Test
    void getProductRecordNotFoundExceptionTest() {
        when(productRepository.findById("6590722a46eb225aac1cfd22")).thenReturn(Optional.empty());
        Exception exception = assertThrows(RecordNotFoundException.class, () -> productService.getProduct("6590722a46eb225aac1cfd22"));
        assertEquals("Product with ID [6590722a46eb225aac1cfd22] not found", exception.getMessage());
        verify(productRepository, times(1)).findById("6590722a46eb225aac1cfd22");
        verify(productMapper, times(0)).productToProductRecord(product);
    }

    @Test
    void getAllProductsTest(){
       Product product2 = new Product("6590722a46eb225aac1cfd23", "iPhone 13", new BigDecimal("48999"),
                "iPhone 13 (128GB) - Purple", LONG_DESC_2, "", "",
                null, null, null, false);
        when(productRepository.findAll()).thenReturn(Arrays.asList(product,product2));
        List<ProductRecord> allProducts = productService.getAllProducts();

        assertEquals(2,allProducts.size());

        ProductRecord productRecordFound = allProducts.get(0);
        assertEquals("6590722a46eb225aac1cfd22", productRecordFound.id());
        assertEquals("iPhone 14", productRecordFound.name());
        assertEquals(new BigDecimal("58999"), productRecordFound.price());
        assertEquals("iPhone 14 (128 GB) - Midnight", productRecordFound.shortDesc());
        assertEquals(LONG_DESC_1, productRecordFound.longDesc());
        assertTrue(productRecordFound.active());

        productRecordFound = allProducts.get(1);
        assertEquals("6590722a46eb225aac1cfd23", productRecordFound.id());
        assertEquals("iPhone 13", productRecordFound.name());
        assertEquals(new BigDecimal("48999"), productRecordFound.price());
        assertEquals("iPhone 13 (128GB) - Purple", productRecordFound.shortDesc());
        assertEquals(LONG_DESC_2, productRecordFound.longDesc());
        assertFalse(productRecordFound.active());
    }

    @Test
    void deleteProductTest(){
        when(productRepository.findById("6590722a46eb225aac1cfd22")).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);
        productService.deleteProduct("6590722a46eb225aac1cfd22");

        assertEquals("6590722a46eb225aac1cfd22", product.getId());
        assertEquals("iPhone 14", product.getName());
        assertEquals(new BigDecimal("58999"), product.getPrice());
        assertEquals("iPhone 14 (128 GB) - Midnight", product.getShortDesc());
        assertEquals(LONG_DESC_1, product.getLongDesc());
        assertNotNull(product.getDeletedOn());
        assertFalse(product.getActive());
    }

    @Test
    void deleteProductRecordNotFoundExceptionTest() {
        when(productRepository.findById("6590722a46eb225aac1cfd22")).thenReturn(Optional.empty());
        Exception exception = assertThrows(RecordNotFoundException.class, () -> productService.deleteProduct("6590722a46eb225aac1cfd22"));
        assertEquals("Product with ID [6590722a46eb225aac1cfd22] not found", exception.getMessage());
        verify(productRepository, times(1)).findById("6590722a46eb225aac1cfd22");
        verify(productRepository, times(0)).save(product);
    }
}
