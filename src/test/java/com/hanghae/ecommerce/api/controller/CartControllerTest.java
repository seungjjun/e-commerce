package com.hanghae.ecommerce.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
class CartControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("장바구니 아이템 추가")
    void addCartItem() throws Exception {
        // Given
        Long userId = 1L;

        // When && Then
        mockMvc.perform(post("/cart-items/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "cartItems": [
                                    {
                                      "productId": 1,
                                      "quantity": 1
                                    },
                                    {
                                      "productId": 2,
                                      "quantity": 10
                                    }
                                  ]
                                }
                                """))
                .andExpect(status().isCreated());
    }
}
