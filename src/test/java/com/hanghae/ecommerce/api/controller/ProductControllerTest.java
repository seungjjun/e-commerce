package com.hanghae.ecommerce.api.controller;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.domain.product.Product;
import com.hanghae.ecommerce.domain.product.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    @MockBean
    private ProductService productService;

    @Test
    @DisplayName("[GET] 상품 목록 조회")
    void getProducts() throws Exception {
        // Given
        Product product1 = Fixtures.product("후드티");
        Product product2 = Fixtures.product("맨투맨");

        given(productService.getProducts()).willReturn(
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

    @Test
    @DisplayName("[GET] 상품 상제 조회 성공")
    void succeed_get_product_detail() throws Exception {
        // Given
        Long productId = 1L;

        Product product = Fixtures.product("후드티");

        given(productService.getProductDetail(productId)).willReturn(product);

        // When && Then
        mockMvc.perform(get("/products/" + productId))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.name").value("후드티"),
                        jsonPath("$.price").value(50000)
                );
    }

    @Test
    @DisplayName("[GET] 인기 상품 조회 성공")
    void succeed_get_popular_products() throws Exception {
        // Given
        Product product1 = Fixtures.product("후드티");
        Product product2 = Fixtures.product("맨투맨");

        given(productService.getPopularProducts()).willReturn(
                List.of(product1, product2)
        );

        // When && Then
        mockMvc.perform(get("/products/popular"))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.products[0].name").value("후드티"),
                        jsonPath("$.products[1].name").value("맨투맨")
                );
    }
}
