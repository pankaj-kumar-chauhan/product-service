package com.cb.product.repository;

import com.cb.product.config.MongoConfig;
import com.cb.product.entity.Product;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@Testcontainers
@ContextConfiguration(classes = {MongoConfig.class, LocalValidatorFactoryBean.class})
@EnableMongoRepositories(basePackages = "com.cb.product.repository")
class ProductRepositoryTest {

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
    @Container
    @ServiceConnection
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.0")
            .withExposedPorts(27017);

    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    void setup() {
        productRepository.deleteAll();
    }

    @Test
    void saveTest() {
        Product product = new Product(null, "iPhone 14", new BigDecimal("58999"),
                "iPhone 14 (128 GB) - Midnight", LONG_DESC_1, "", "",
                null, null, null, true);
        Product savedProduct = productRepository.save(product);
        assertNotNull(savedProduct.getId());
        assertNotNull(savedProduct.getCreatedOn());
        assertNotNull(savedProduct.getUpdatedOn());
        assertEquals("iPhone 14", savedProduct.getName());
        assertEquals(new BigDecimal("58999"), savedProduct.getPrice());
        assertEquals("iPhone 14 (128 GB) - Midnight", savedProduct.getShortDesc());
        assertEquals(LONG_DESC_1, savedProduct.getLongDesc());
        assertTrue(savedProduct.getActive());
    }

    @Test
    void saveValidateNameNullTest() {
        Product product = new Product(null, null, new BigDecimal("58999"),
                "iPhone 14 (128 GB) - Midnight", LONG_DESC_1, "", "",
                null, null, null, true);
        Exception exception = assertThrows(ConstraintViolationException.class, () -> productRepository.save(product));
        assertEquals("name: must not be blank",exception.getMessage());
    }

    @Test
    void saveValidateNameBlankTest() {
        Product product = new Product(null, "", new BigDecimal("58999"),
                "iPhone 14 (128 GB) - Midnight", LONG_DESC_1, "", "",
                null, null, null, true);
        Exception exception = assertThrows(ConstraintViolationException.class, () -> productRepository.save(product));
        assertEquals("name: must not be blank",exception.getMessage());
    }

    @Test
    void saveValidatePriceNullTest() {
        Product product = new Product(null, "iPhone 14", null,
                "iPhone 14 (128 GB) - Midnight", LONG_DESC_1, "", "",
                null, null, null, true);
        Exception exception = assertThrows(ConstraintViolationException.class, () -> productRepository.save(product));
        assertEquals("price: must not be null",exception.getMessage());
    }

    @Test
    void saveValidatePriceNegativeTest() {
        Product product = new Product(null, "iPhone 14", new BigDecimal("-58999"),
                "iPhone 14 (128 GB) - Midnight", LONG_DESC_1, "", "",
                null, null, null, true);
        Exception exception = assertThrows(ConstraintViolationException.class, () -> productRepository.save(product));
        assertEquals("price: must be greater than or equal to 0",exception.getMessage());
    }

    @Test
    void saveValidateShortDescNullTest() {
        Product product = new Product(null, "iPhone 14", new BigDecimal("58999"),
                null, LONG_DESC_1, "", "",
                null, null, null, true);
        Exception exception = assertThrows(ConstraintViolationException.class, () -> productRepository.save(product));
        assertEquals("shortDesc: must not be blank",exception.getMessage());
    }

    @Test
    void saveValidateShortDescBlankTest() {
        Product product = new Product(null, "iPhone 14", new BigDecimal("58999"),
                "", LONG_DESC_1, "", "",
                null, null, null, true);
        Exception exception = assertThrows(ConstraintViolationException.class, () -> productRepository.save(product));
        assertEquals("shortDesc: must not be blank",exception.getMessage());
    }

    @Test
    void findByIdTest() {
        Product product = new Product(null, "iPhone 14", new BigDecimal("58999"),
                "iPhone 14 (128 GB) - Midnight", LONG_DESC_1, "", "",
                null, null, null, true);
        Product savedProduct = productRepository.save(product);
        Optional<Product> productFoundOpt = productRepository.findById(savedProduct.getId());
        assertTrue(productFoundOpt.isPresent());
        productFoundOpt.ifPresent(productFound -> {
            assertNotNull(productFound.getId());
            assertNotNull(productFound.getCreatedOn());
            assertNotNull(productFound.getUpdatedOn());
            assertEquals("iPhone 14", productFound.getName());
            assertEquals(new BigDecimal("58999"), productFound.getPrice());
            assertEquals("iPhone 14 (128 GB) - Midnight", productFound.getShortDesc());
            assertEquals(LONG_DESC_1, productFound.getLongDesc());
            assertTrue(productFound.getActive());
        });

    }

    @Test
    void findByIdInvalidIdTest() {
        Product product = new Product(null, "iPhone 14", new BigDecimal("58999"),
                "iPhone 14 (128 GB) - Midnight", LONG_DESC_1, "", "",
                null, null, null, true);
        Product savedProduct = productRepository.save(product);
        Optional<Product> productFoundOpt = productRepository.findById(savedProduct.getId() + "99");
        assertTrue(productFoundOpt.isEmpty());
    }

    @Test
    void findAllTest() {
        Product product1 = new Product(null, "iPhone 14", new BigDecimal("58999"),
                "iPhone 14 (128 GB) - Midnight", LONG_DESC_1, "", "",
                null, null, null, true);
        productRepository.save(product1);
        Product product2 = new Product(null, "iPhone 13", new BigDecimal("48999"),
                "iPhone 13 (128GB) - Purple", LONG_DESC_2, "", "",
                null, null, null, false);
        productRepository.save(product2);
        List<Product> products = productRepository.findAll();

        assertEquals(2, products.size());

        Product productFound = products.get(0);
        assertEquals(product1.getId(), productFound.getId());
        assertEquals("iPhone 14", productFound.getName());
        assertEquals(new BigDecimal("58999"), productFound.getPrice());
        assertEquals("iPhone 14 (128 GB) - Midnight", productFound.getShortDesc());
        assertEquals(LONG_DESC_1, productFound.getLongDesc());
        assertEquals(product1.getCreatedOn().withNano(0), productFound.getCreatedOn().withNano(0));
        assertEquals(product1.getUpdatedOn().withNano(0), productFound.getUpdatedOn().withNano(0));
        assertTrue(productFound.getActive());

        productFound = products.get(1);
        assertEquals(product2.getId(), productFound.getId());
        assertEquals("iPhone 13", productFound.getName());
        assertEquals(new BigDecimal("48999"), productFound.getPrice());
        assertEquals("iPhone 13 (128GB) - Purple", productFound.getShortDesc());
        assertEquals(LONG_DESC_2, productFound.getLongDesc());
        assertEquals(product2.getCreatedOn().withNano(0), productFound.getCreatedOn().withNano(0));
        assertEquals(product2.getUpdatedOn().withNano(0), productFound.getUpdatedOn().withNano(0));
        assertFalse(productFound.getActive());
    }

}
