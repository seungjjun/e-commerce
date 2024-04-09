package com.hanghae.ecommerce.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("")
    void test() throws Exception {
        // Given
        Long userId = 1L;

        // When && Then
        mockMvc.perform(post("/payments/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                   "orderId" : 1,
                                   "amount" : 50000,
                                   "paymentMethod" : "CARD"
                                }
                                """))
                .andExpect(status().isCreated());
    }

}
