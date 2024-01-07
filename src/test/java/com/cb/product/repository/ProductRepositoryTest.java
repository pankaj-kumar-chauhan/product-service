package com.cb.product.repository;

import com.cb.product.config.MongoConfig;
import com.cb.product.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@Testcontainers
@ContextConfiguration(classes = {MongoConfig.class})
@EnableMongoRepositories(basePackages = "com.cb.product.repository")
class ProductRepositoryTest {

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
                "iPhone 14 (128 GB) - Midnight", """
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
                """, "", "",
                null, null, null, true);
        Product savedProduct = productRepository.save(product);
        assertNotNull(savedProduct.getId());
        assertNotNull(savedProduct.getCreatedOn());
        assertNotNull(savedProduct.getUpdatedOn());
        assertEquals("iPhone 14", savedProduct.getName());
        assertEquals(new BigDecimal("58999"), savedProduct.getPrice());
        assertEquals("iPhone 14 (128 GB) - Midnight", savedProduct.getShortDesc());
        assertEquals("""
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
                """, savedProduct.getLongDesc());
        assertTrue(savedProduct.getActive());
    }

    @Test
    void findByIdTest() {
        Product product = new Product(null, "iPhone 14", new BigDecimal("58999"),
                "iPhone 14 (128 GB) - Midnight", """
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
                """, "", "",
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
            assertEquals("""
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
                    """, productFound.getLongDesc());
            assertTrue(productFound.getActive());
        });

    }

    @Test
    void findByIdInvalidIdTest() {
        Product product = new Product(null, "iPhone 14", new BigDecimal("58999"),
                "iPhone 14 (128 GB) - Midnight", """
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
                """, "", "",
                null, null, null, true);
        Product savedProduct = productRepository.save(product);
        Optional<Product> productFoundOpt = productRepository.findById(savedProduct.getId() + "99");
        assertTrue(productFoundOpt.isEmpty());
    }

    @Test
    void findAllTest() {
        Product product1 = new Product(null, "iPhone 14", new BigDecimal("58999"),
                "iPhone 14 (128 GB) - Midnight", """
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
                """, "", "",
                null, null, null, true);
        productRepository.save(product1);
        Product product2 = new Product(null, "iPhone 13", new BigDecimal("48999"),
                "iPhone 13 (128GB) - Purple", """
                About this item
                15 cm (6.1-inch) Super Retina XDR display
                Cinematic mode adds shallow depth of field and shifts focus automatically in your videos
                Advanced dual-camera system with 12MP Wide and Ultra Wide cameras; Photographic Styles, Smart HDR 4, Night mode, 4K Dolby Vision HDR recording
                12MP TrueDepth front camera with Night mode, 4K Dolby Vision HDR recording
                A15 Bionic chip for lightning-fast performance
                """, "", "",
                null, null, null, false);
        productRepository.save(product2);
        List<Product> products = productRepository.findAll();

        assertEquals(2, products.size());

        Product productFound = products.get(0);
        assertEquals(product1.getId(), productFound.getId());
        assertEquals("iPhone 14", productFound.getName());
        assertEquals(new BigDecimal("58999"), productFound.getPrice());
        assertEquals("iPhone 14 (128 GB) - Midnight", productFound.getShortDesc());
        assertEquals("""
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
                """, productFound.getLongDesc());
        assertEquals(product1.getCreatedOn().withNano(0), productFound.getCreatedOn().withNano(0));
        assertEquals(product1.getUpdatedOn().withNano(0), productFound.getUpdatedOn().withNano(0));
        assertTrue(productFound.getActive());

        productFound = products.get(1);
        assertEquals(product2.getId(), productFound.getId());
        assertEquals("iPhone 13", productFound.getName());
        assertEquals(new BigDecimal("48999"), productFound.getPrice());
        assertEquals("iPhone 13 (128GB) - Purple", productFound.getShortDesc());
        assertEquals("""
                About this item
                15 cm (6.1-inch) Super Retina XDR display
                Cinematic mode adds shallow depth of field and shifts focus automatically in your videos
                Advanced dual-camera system with 12MP Wide and Ultra Wide cameras; Photographic Styles, Smart HDR 4, Night mode, 4K Dolby Vision HDR recording
                12MP TrueDepth front camera with Night mode, 4K Dolby Vision HDR recording
                A15 Bionic chip for lightning-fast performance
                """, productFound.getLongDesc());
        assertEquals(product2.getCreatedOn().withNano(0), productFound.getCreatedOn().withNano(0));
        assertEquals(product2.getUpdatedOn().withNano(0), productFound.getUpdatedOn().withNano(0));
        assertFalse(productFound.getActive());
    }

}
