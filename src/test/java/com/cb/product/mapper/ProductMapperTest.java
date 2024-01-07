package com.cb.product.mapper;

import com.cb.product.entity.Product;
import com.cb.product.record.ProductRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class ProductMapperTest {

    @Spy
    ProductMapperImpl productMapper;

    @Test
    void productToProductRecordTest() {
        LocalDateTime localDateTime = LocalDateTime.now();
        Product product = new Product("6590722a46eb225aac1cfd22", "iPhone 14",
                new BigDecimal("58999"), "iPhone 14 (128 GB) - Midnight", """
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
                """,
                "https://https://testimage.com/iphone14/image1.png.com/iphone14/thumbnail.png",
                "https://testimage.com/iphone14/image1.png", localDateTime, localDateTime, localDateTime,
                true);

        ProductRecord productRecord = productMapper.productToProductRecord(product);
        assertEquals("6590722a46eb225aac1cfd22", productRecord.id());
        assertEquals("iPhone 14", productRecord.name());
        assertEquals(new BigDecimal("58999"), productRecord.price());
        assertEquals("iPhone 14 (128 GB) - Midnight", productRecord.shortDesc());
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
                """, productRecord.longDesc());
        assertEquals("https://https://testimage.com/iphone14/image1.png.com/iphone14/thumbnail.png", productRecord.thumbnail());
        assertEquals("https://testimage.com/iphone14/image1.png", productRecord.image());
        assertEquals(localDateTime, productRecord.createdOn());
        assertEquals(localDateTime, productRecord.updatedOn());
        assertEquals(localDateTime, productRecord.deletedOn());
        assertTrue(productRecord.active());
    }

    @Test
    void nullProductToProductRecordTest() {
        ProductRecord productRecord = productMapper.productToProductRecord(null);
        assertNull(productRecord);
    }

    @Test
    void productsToProductRecordsTest() {
        LocalDateTime localDateTime = LocalDateTime.now();
        Product product = new Product("6590722a46eb225aac1cfd22", "iPhone 14",
                new BigDecimal("58999"), "iPhone 14 (128 GB) - Midnight", """
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
                """,
                "https://https://testimage.com/iphone14/image1.png.com/iphone14/thumbnail.png",
                "https://testimage.com/iphone14/image1.png", localDateTime, localDateTime, localDateTime,
                true);

        LocalDateTime localDateTime2 = LocalDateTime.now();
        Product product2 = new Product("6590722a46eb225aac1cfd23", "iPhone 13",
                new BigDecimal("30.30"), "iPhone 13 (128GB) - Purple", """
                About this item
                15 cm (6.1-inch) Super Retina XDR display
                Cinematic mode adds shallow depth of field and shifts focus automatically in your videos
                Advanced dual-camera system with 12MP Wide and Ultra Wide cameras; Photographic Styles, Smart HDR 4, Night mode, 4K Dolby Vision HDR recording
                12MP TrueDepth front camera with Night mode, 4K Dolby Vision HDR recording
                A15 Bionic chip for lightning-fast performance
                """,
                "https://https://testimage.com/iphone14/image1.png.com/iphone13/thumbnail.png",
                "https://testimage.com/iphone13/image1.png", localDateTime2, localDateTime2, localDateTime2,
                false);

        List<ProductRecord> productRecords = productMapper.productsToProductRecords(Arrays.asList(product, product2));

        assertEquals(2, productRecords.size());

        ProductRecord productRecord = productRecords.get(0);
        assertEquals("6590722a46eb225aac1cfd22", productRecord.id());
        assertEquals("iPhone 14", productRecord.name());
        assertEquals(new BigDecimal("58999"), productRecord.price());
        assertEquals("iPhone 14 (128 GB) - Midnight", productRecord.shortDesc());
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
                """, productRecord.longDesc());
        assertEquals("https://https://testimage.com/iphone14/image1.png.com/iphone14/thumbnail.png", productRecord.thumbnail());
        assertEquals("https://testimage.com/iphone14/image1.png", productRecord.image());
        assertEquals(localDateTime, productRecord.createdOn());
        assertEquals(localDateTime, productRecord.updatedOn());
        assertEquals(localDateTime, productRecord.deletedOn());
        assertTrue(productRecord.active());

        productRecord = productRecords.get(1);
        assertEquals("6590722a46eb225aac1cfd23", productRecord.id());
        assertEquals("iPhone 13", productRecord.name());
        assertEquals(new BigDecimal("30.30"), productRecord.price());
        assertEquals("iPhone 13 (128GB) - Purple", productRecord.shortDesc());
        assertEquals("""
                About this item
                15 cm (6.1-inch) Super Retina XDR display
                Cinematic mode adds shallow depth of field and shifts focus automatically in your videos
                Advanced dual-camera system with 12MP Wide and Ultra Wide cameras; Photographic Styles, Smart HDR 4, Night mode, 4K Dolby Vision HDR recording
                12MP TrueDepth front camera with Night mode, 4K Dolby Vision HDR recording
                A15 Bionic chip for lightning-fast performance
                """, productRecord.longDesc());
        assertEquals("https://https://testimage.com/iphone14/image1.png.com/iphone13/thumbnail.png", productRecord.thumbnail());
        assertEquals("https://testimage.com/iphone13/image1.png", productRecord.image());
        assertEquals(localDateTime2, productRecord.createdOn());
        assertEquals(localDateTime2, productRecord.updatedOn());
        assertEquals(localDateTime2, productRecord.deletedOn());
        assertFalse(productRecord.active());
    }

    @Test
    void nullProductsToProductRecordsTest() {
        List<ProductRecord> productRecords = productMapper.productsToProductRecords(null);
        assertNull(productRecords);
    }

    @Test
    void productRecordToProductTest() {
        LocalDateTime localDateTime = LocalDateTime.now();
        ProductRecord productRecord = new ProductRecord("6590722a46eb225aac1cfd22", "iPhone 14", new BigDecimal("58999"),
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
                """, "https://https://testimage.com/iphone14/image1.png.com/iphone14/thumbnail.png", "https://testimage.com/iphone14/image1.png",
                localDateTime, localDateTime, localDateTime, true);
        Product product = productMapper.productRecordToProduct(productRecord);
        assertEquals("6590722a46eb225aac1cfd22", product.getId());
        assertEquals("iPhone 14", product.getName());
        assertEquals(new BigDecimal("58999"), product.getPrice());
        assertEquals("iPhone 14 (128 GB) - Midnight", product.getShortDesc());
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
                """, product.getLongDesc());
        assertEquals("https://https://testimage.com/iphone14/image1.png.com/iphone14/thumbnail.png", product.getThumbnail());
        assertEquals("https://testimage.com/iphone14/image1.png", product.getImage());
        assertEquals(localDateTime, product.getCreatedOn());
        assertEquals(localDateTime, product.getUpdatedOn());
        assertEquals(localDateTime, product.getDeletedOn());
        assertTrue(product.getActive());
    }

    @Test
    void nullProductRecordToProductTest() {
        Product product = productMapper.productRecordToProduct(null);
        assertNull(product);
    }
}
