package com.hanghae.ecommerce.api.controller;

import com.hanghae.ecommerce.application.cart.CartUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CartController.class)
class CartControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartUseCase cartUseCase;

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
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("상품이 추가 되었습니다."));
    }
}
