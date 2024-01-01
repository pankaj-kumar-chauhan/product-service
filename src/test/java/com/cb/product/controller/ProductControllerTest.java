package com.cb.product.controller;


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

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductService productService;

    ProductRecord productRecord;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        productRecord = new ProductRecord("testID", "testName", new BigDecimal("20.30"),
                "testShortDesc", "testLongDesc", "", "",
                null, null, null, true);
    }

    @Test
    void saveTest() throws Exception {
        productRecord = new ProductRecord(null, "testName", new BigDecimal("20.30"),
                "testShortDesc", "testLongDesc", "", "",
                null, null, null, true);
        when(productService.save(productRecord)).thenReturn(productRecord);
        mockMvc.perform(post("/api/v1/product")
                        .content(objectMapper.writeValueAsString(productRecord))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("testName")))
                .andExpect(jsonPath("$.price", is(20.30)))
                .andExpect(jsonPath("$.shortDesc", is("testShortDesc")))
                .andExpect(jsonPath("$.longDesc", is("testLongDesc")))
                .andExpect(jsonPath("$.active", is(true)));
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
                .andExpect(jsonPath("$.id", is("testID")))
                .andExpect(jsonPath("$.name", is("testName")))
                .andExpect(jsonPath("$.price", is(20.30)))
                .andExpect(jsonPath("$.shortDesc", is("testShortDesc")))
                .andExpect(jsonPath("$.longDesc", is("testLongDesc")))
                .andExpect(jsonPath("$.active", is(true)));
    }

    @Test
    void getProductTest() throws Exception {
        when(productService.getProduct("testID")).thenReturn(productRecord);
        mockMvc.perform(get("/api/v1/product/testID"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is("testID")))
                .andExpect(jsonPath("$.name", is("testName")))
                .andExpect(jsonPath("$.price", is(20.30)))
                .andExpect(jsonPath("$.shortDesc", is("testShortDesc")))
                .andExpect(jsonPath("$.longDesc", is("testLongDesc")))
                .andExpect(jsonPath("$.active", is(true)));
    }

    @Test
    void getAllProductTest() throws Exception {
        ProductRecord productRecord2 = new ProductRecord("testID2", "testName2", new BigDecimal("30.30"),
                "testShortDesc2", "testLongDesc2", "", "",
                null, null, null, false);
        when(productService.getAllProducts()).thenReturn(Arrays.asList(productRecord, productRecord2));
        mockMvc.perform(get("/api/v1/product"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id", is("testID")))
                .andExpect(jsonPath("$.[0].name", is("testName")))
                .andExpect(jsonPath("$.[0].price", is(20.30)))
                .andExpect(jsonPath("$.[0].shortDesc", is("testShortDesc")))
                .andExpect(jsonPath("$.[0].longDesc", is("testLongDesc")))
                .andExpect(jsonPath("$.[0].active", is(true)))
                .andExpect(jsonPath("$.[1].id", is("testID2")))
                .andExpect(jsonPath("$.[1].name", is("testName2")))
                .andExpect(jsonPath("$.[1].price", is(30.30)))
                .andExpect(jsonPath("$.[1].shortDesc", is("testShortDesc2")))
                .andExpect(jsonPath("$.[1].longDesc", is("testLongDesc2")))
                .andExpect(jsonPath("$.[1].active", is(false)));
    }

    @Test
    void deleteProductTest() throws Exception {
        mockMvc.perform(delete("/api/v1/product/testID"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
