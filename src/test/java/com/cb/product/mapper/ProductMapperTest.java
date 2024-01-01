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
        Product product = new Product("testID", "testName",
                new BigDecimal("20.30"), "testShortDesc", "testLongDesc",
                "testThumbnail", "testImage", localDateTime, localDateTime, localDateTime,
                true);

        ProductRecord productRecord = productMapper.productToProductRecord(product);
        assertEquals("testID", productRecord.id());
        assertEquals("testName", productRecord.name());
        assertEquals(new BigDecimal("20.30"), productRecord.price());
        assertEquals("testShortDesc", productRecord.shortDesc());
        assertEquals("testLongDesc", productRecord.longDesc());
        assertEquals("testThumbnail", productRecord.thumbnail());
        assertEquals("testImage", productRecord.image());
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
        Product product = new Product("testID", "testName",
                new BigDecimal("20.30"), "testShortDesc", "testLongDesc",
                "testThumbnail", "testImage", localDateTime, localDateTime, localDateTime,
                true);

        LocalDateTime localDateTime2 = LocalDateTime.now();
        Product product2 = new Product("testID2", "testName2",
                new BigDecimal("30.30"), "testShortDesc2", "testLongDesc2",
                "testThumbnail2", "testImage2", localDateTime2, localDateTime2, localDateTime2,
                false);

        List<ProductRecord> productRecords = productMapper.productsToProductRecords(Arrays.asList(product, product2));

        assertEquals(2, productRecords.size());

        ProductRecord productRecord = productRecords.get(0);
        assertEquals("testID", productRecord.id());
        assertEquals("testName", productRecord.name());
        assertEquals(new BigDecimal("20.30"), productRecord.price());
        assertEquals("testShortDesc", productRecord.shortDesc());
        assertEquals("testLongDesc", productRecord.longDesc());
        assertEquals("testThumbnail", productRecord.thumbnail());
        assertEquals("testImage", productRecord.image());
        assertEquals(localDateTime, productRecord.createdOn());
        assertEquals(localDateTime, productRecord.updatedOn());
        assertEquals(localDateTime, productRecord.deletedOn());
        assertTrue(productRecord.active());

        productRecord = productRecords.get(1);
        assertEquals("testID2", productRecord.id());
        assertEquals("testName2", productRecord.name());
        assertEquals(new BigDecimal("30.30"), productRecord.price());
        assertEquals("testShortDesc2", productRecord.shortDesc());
        assertEquals("testLongDesc2", productRecord.longDesc());
        assertEquals("testThumbnail2", productRecord.thumbnail());
        assertEquals("testImage2", productRecord.image());
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
        ProductRecord productRecord = new ProductRecord("testID", "testName", new BigDecimal("20.30"),
                "testShortDesc", "testLongDesc", "testThumbnail", "testImage",
                localDateTime, localDateTime, localDateTime, true);
        Product product = productMapper.productRecordToProduct(productRecord);
        assertEquals("testID", product.getId());
        assertEquals("testName", product.getName());
        assertEquals(new BigDecimal("20.30"), product.getPrice());
        assertEquals("testShortDesc", product.getShortDesc());
        assertEquals("testLongDesc", product.getLongDesc());
        assertEquals("testThumbnail", product.getThumbnail());
        assertEquals("testImage", product.getImage());
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
