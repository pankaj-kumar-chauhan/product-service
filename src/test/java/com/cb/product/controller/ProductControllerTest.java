package com.cb.product.controller;


import com.cb.product.exception.RecordNotFoundException;
import com.cb.product.record.ProductRecord;
import com.cb.product.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
class ProductControllerTest {

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
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductService productService;

    ProductRecord productRecord;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        productRecord = new ProductRecord("6590722a46eb225aac1cfd22", "iPhone 14", new BigDecimal("58999"),
                "iPhone 14 (128 GB) - Midnight", LONG_DESC_1, "", "",
                null, null, null, true);
    }

    @Test
    void saveTest() throws Exception {
        productRecord = new ProductRecord(null, "iPhone 14", new BigDecimal("58999"),
                "iPhone 14 (128 GB) - Midnight", LONG_DESC_1, "", "",
                null, null, null, true);
        when(productService.save(productRecord)).thenReturn(productRecord);
        mockMvc.perform(post("/api/v1/product")
                        .content(objectMapper.writeValueAsString(productRecord))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("iPhone 14")))
                .andExpect(jsonPath("$.price", is(58999)))
                .andExpect(jsonPath("$.shortDesc", is("iPhone 14 (128 GB) - Midnight")))
                .andExpect(jsonPath("$.longDesc", is(LONG_DESC_1)))
                .andExpect(jsonPath("$.active", is(true)));
    }

    @Test
    void saveValidateNameNullTest() throws Exception {
        productRecord = new ProductRecord(null, "", new BigDecimal("58999"),
                "iPhone 14 (128 GB) - Midnight", LONG_DESC_1, "", "",
                null, null, null, true);
        mockMvc.perform(post("/api/v1/product")
                        .content(objectMapper.writeValueAsString(productRecord))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("must not be blank")));
    }
    @Test
    void saveValidateNameBlankTest() throws Exception {
        productRecord = new ProductRecord(null, "", new BigDecimal("58999"),
                "iPhone 14 (128 GB) - Midnight", LONG_DESC_1, "", "",
                null, null, null, true);
        mockMvc.perform(post("/api/v1/product")
                        .content(objectMapper.writeValueAsString(productRecord))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("must not be blank")));
    }

    @Test
    void saveValidatePriceNullTest() throws Exception {
        productRecord = new ProductRecord(null, "iPhone 14",null,
                "iPhone 14 (128 GB) - Midnight", LONG_DESC_1, "", "",
                null, null, null, true);
        mockMvc.perform(post("/api/v1/product")
                        .content(objectMapper.writeValueAsString(productRecord))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.price", is("must not be null")));
    }

    @Test
    void saveValidatePriceNegativeTest() throws Exception {
        productRecord = new ProductRecord(null, "iPhone 14",new BigDecimal("-58999"),
                "iPhone 14 (128 GB) - Midnight", LONG_DESC_1, "", "",
                null, null, null, true);
        mockMvc.perform(post("/api/v1/product")
                        .content(objectMapper.writeValueAsString(productRecord))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.price", is("must be greater than or equal to 0")));
    }

    @Test
    void saveValidateShortDescNullTest() throws Exception {
        productRecord = new ProductRecord(null, "iPhone 14",new BigDecimal("58999"),
                null, LONG_DESC_1, "", "",
                null, null, null, true);
        mockMvc.perform(post("/api/v1/product")
                        .content(objectMapper.writeValueAsString(productRecord))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.shortDesc", is("must not be blank")));
    }

    @Test
    void saveValidateShortDescBlankTest() throws Exception {
        productRecord = new ProductRecord(null, "iPhone 14",new BigDecimal("58999"),
                "", LONG_DESC_1, "", "",
                null, null, null, true);
        mockMvc.perform(post("/api/v1/product")
                        .content(objectMapper.writeValueAsString(productRecord))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.shortDesc", is("must not be blank")));
    }

    @Test
    void updateTest() throws Exception {
        when(productService.save(productRecord)).thenReturn(productRecord);
        mockMvc.perform(post("/api/v1/product")
                        .content(objectMapper.writeValueAsString(productRecord))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is("6590722a46eb225aac1cfd22")))
                .andExpect(jsonPath("$.name", is("iPhone 14")))
                .andExpect(jsonPath("$.price", is(58999)))
                .andExpect(jsonPath("$.shortDesc", is("iPhone 14 (128 GB) - Midnight")))
                .andExpect(jsonPath("$.longDesc", is(LONG_DESC_1)))
                .andExpect(jsonPath("$.active", is(true)));
    }

    @Test
    void getProductTest() throws Exception {
        when(productService.getProduct("6590722a46eb225aac1cfd22")).thenReturn(productRecord);
        mockMvc.perform(get("/api/v1/product/6590722a46eb225aac1cfd22"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is("6590722a46eb225aac1cfd22")))
                .andExpect(jsonPath("$.name", is("iPhone 14")))
                .andExpect(jsonPath("$.price", is(58999)))
                .andExpect(jsonPath("$.shortDesc", is("iPhone 14 (128 GB) - Midnight")))
                .andExpect(jsonPath("$.longDesc", is(LONG_DESC_1)))
                .andExpect(jsonPath("$.active", is(true)));
    }

    @Test
    void getProductInvalidIdTest() throws Exception {
        when(productService.getProduct("6590722a46eb225aac1cfd22")).thenThrow(new RecordNotFoundException("Product with ID [6590722a46eb225aac1cfd22] not found"));
        mockMvc.perform(get("/api/v1/product/6590722a46eb225aac1cfd22"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
                .andExpect(content().string("Product with ID [6590722a46eb225aac1cfd22] not found"));
    }

    @Test
    void getAllProductTest() throws Exception {
        ProductRecord productRecord2 = new ProductRecord("6590722a46eb225aac1cfd23", "iPhone 13", new BigDecimal("49999"),
                "iPhone 13 (128GB) - Purple", LONG_DESC_2, "", "",
                null, null, null, false);
        when(productService.getAllProducts()).thenReturn(Arrays.asList(productRecord, productRecord2));
        mockMvc.perform(get("/api/v1/product"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id", is("6590722a46eb225aac1cfd22")))
                .andExpect(jsonPath("$.[0].name", is("iPhone 14")))
                .andExpect(jsonPath("$.[0].price", is(58999)))
                .andExpect(jsonPath("$.[0].shortDesc", is("iPhone 14 (128 GB) - Midnight")))
                .andExpect(jsonPath("$.[0].longDesc", is(LONG_DESC_1)))
                .andExpect(jsonPath("$.[0].active", is(true)))
                .andExpect(jsonPath("$.[1].id", is("6590722a46eb225aac1cfd23")))
                .andExpect(jsonPath("$.[1].name", is("iPhone 13")))
                .andExpect(jsonPath("$.[1].price", is(49999)))
                .andExpect(jsonPath("$.[1].shortDesc", is("iPhone 13 (128GB) - Purple")))
                .andExpect(jsonPath("$.[1].longDesc", is(LONG_DESC_2)))
                .andExpect(jsonPath("$.[1].active", is(false)));
    }

    @Test
    void deleteProductTest() throws Exception {
        mockMvc.perform(delete("/api/v1/product/6590722a46eb225aac1cfd22"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
