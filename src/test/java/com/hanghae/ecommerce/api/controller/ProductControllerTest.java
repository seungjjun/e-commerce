package com.hanghae.ecommerce.api.controller;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.domain.product.Product;
import com.hanghae.ecommerce.domain.product.ProductCoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductCoreService productCoreService;

    @Test
    @DisplayName("[GET] 상품 목록 조회")
    void getProducts() throws Exception {
        // Given
        Product product1 = Fixtures.product("후드티");
        Product product2 = Fixtures.product("맨투맨");

        given(productCoreService.getProducts()).willReturn(
                List.of(product1, product2));

        // When && Then
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.products[0].name").value("후드티"),
                        jsonPath("$.products[0].price").value(50000),
                        jsonPath("$.products[1].name").value("맨투맨"),
                        jsonPath("$.products[1].price").value(39000));
    }
}
