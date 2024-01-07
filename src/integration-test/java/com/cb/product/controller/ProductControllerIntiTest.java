package com.cb.product.controller;


import com.cb.product.entity.Product;
import com.cb.product.record.ProductRecord;
import com.cb.product.repository.ProductRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.Optional;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerIntiTest {

    @Container
    @ServiceConnection
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.0")
            .withExposedPorts(27017);

    @Autowired
    ProductRepository productRepository;
    @LocalServerPort
    private int localServerPort;

    @BeforeEach
    void setup() {
        baseURI = "http://localhost:" + localServerPort;
    }

    @Test
    void saveTest() {
        ProductRecord productRecord = new ProductRecord(null, "iPhone 14", new BigDecimal("58999"),
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
        given().
                contentType(ContentType.JSON)
                .body(productRecord)
                .log().all().

                when()
                .post("/api/v1/product")

                .then()
                .log().all()
                .assertThat().statusCode(201)
                .contentType(ContentType.JSON)
                .body("id", notNullValue())
                .body("name", is("iPhone 14"))
                .body("price", is(58999))
                .body("shortDesc", is("iPhone 14 (128 GB) - Midnight"))
                .body("longDesc", is("""
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
                """))
                .body("thumbnail", is(""))
                .body("image", is(""))
                .body("createdOn", is(notNullValue()))
                .body("updatedOn", is(notNullValue()))
                .body("active", is(true));
    }

    @Test
    void updateTest() {
        productRepository.deleteAll();
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
        Product productSaved = productRepository.save(product);
        ProductRecord productRecord = new ProductRecord(productSaved.getId(), "iPhone 13", productSaved.getPrice(),
                productSaved.getShortDesc(), productSaved.getLongDesc(), productSaved.getThumbnail(), productSaved.getImage(),
                productSaved.getCreatedOn(), productSaved.getUpdatedOn(), null, false);
        given().
                contentType(ContentType.JSON)
                .body(productRecord)
                .log().all().

                when()
                .post("/api/v1/product")

                .then()
                .log().all()
                .assertThat().statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", is(productSaved.getId()))
                .body("name", is("iPhone 13"))
                .body("price", is(58999))
                .body("shortDesc", is("iPhone 14 (128 GB) - Midnight"))
                .body("longDesc", is("""
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
                """))
                .body("thumbnail", is(""))
                .body("image", is(""))
                .body("createdOn", is(notNullValue()))
                .body("updatedOn", is(notNullValue()))
                .body("active", is(false));
    }

    @Test
    void getProductTest() {
        productRepository.deleteAll();
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
        Product productSaved = productRepository.save(product);
        given().
                contentType(ContentType.JSON)
                .pathParam("id",productSaved.getId())
                .log().all().

        when()
                .get("/api/v1/product/{id}")

        .then()
                .log().all()
                .assertThat().statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", is(productSaved.getId()))
                .body("name", is("iPhone 14"))
                .body("price", is(58999))
                .body("shortDesc", is("iPhone 14 (128 GB) - Midnight"))
                .body("longDesc", is("""
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
                """))
                .body("thumbnail", is(""))
                .body("image", is(""))
                .body("createdOn", is(notNullValue()))
                .body("updatedOn", is(notNullValue()))
                .body("active", is(true));
    }

    @Test
    void getAllProductTest() {
        productRepository.deleteAll();
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
        Product productSaved = productRepository.save(product);
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
        Product productSaved2 = productRepository.save(product2);

        given().
                contentType(ContentType.JSON)
                .log().all().

                when()
                .get("/api/v1/product")

                .then()
                .log().all()
                .assertThat().statusCode(200)
                .contentType(ContentType.JSON)
                .body("id[0]", is(productSaved.getId()))
                .body("name[0]", is("iPhone 14"))
                .body("price[0]", is(58999))
                .body("shortDesc[0]", is("iPhone 14 (128 GB) - Midnight"))
                .body("longDesc[0]", is("""
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
                """))
                .body("thumbnail[0]", is(""))
                .body("image[0]", is(""))
                .body("createdOn[0]", is(notNullValue()))
                .body("updatedOn[0]", is(notNullValue()))
                .body("active[0]", is(true))

                .body("id[1]", is(productSaved2.getId()))
                .body("name[1]", is("iPhone 13"))
                .body("price[1]", is(48999))
                .body("shortDesc[1]", is("iPhone 13 (128GB) - Purple"))
                .body("longDesc[1]", is("""
                About this item
                15 cm (6.1-inch) Super Retina XDR display
                Cinematic mode adds shallow depth of field and shifts focus automatically in your videos
                Advanced dual-camera system with 12MP Wide and Ultra Wide cameras; Photographic Styles, Smart HDR 4, Night mode, 4K Dolby Vision HDR recording
                12MP TrueDepth front camera with Night mode, 4K Dolby Vision HDR recording
                A15 Bionic chip for lightning-fast performance
                """))
                .body("thumbnail[1]", is(""))
                .body("image[1]", is(""))
                .body("createdOn[1]", is(notNullValue()))
                .body("updatedOn[1]", is(notNullValue()))
                .body("active[1]", is(false));
    }

    @Test
    void deleteProductTest() {
        productRepository.deleteAll();
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
        Product productSaved = productRepository.save(product);

        given().
                contentType(ContentType.JSON)
                .pathParam("id",productSaved.getId())
                .log().all().

                when()
                .delete("/api/v1/product/{id}")

                .then()
                .log().all()
                .assertThat().statusCode(200)
                .contentType(ContentType.TEXT);

        Optional<Product> productFound = productRepository.findById(productSaved.getId());
        assertTrue(productFound.isPresent());
        assertNotNull(productFound.get().getDeletedOn());
    }

}
