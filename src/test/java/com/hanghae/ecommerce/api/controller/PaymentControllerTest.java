package com.hanghae.ecommerce.api.controller;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.domain.payment.Payment;
import com.hanghae.ecommerce.domain.payment.PaymentCoreService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentCoreService paymentCoreService;

    @Test
    @DisplayName("결제 요청 성공")
    void succeed_post_payment() throws Exception {
        // Given
        Long userId = 1L;

        Payment payment = Fixtures.payment(1L);

        given(paymentCoreService.pay(anyLong(), any())).willReturn(payment);

        // When && Then
        mockMvc.perform(post("/payments/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                   "orderId" : 1,
                                   "payAmount" : 89000,
                                   "paymentMethod" : "CARD"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpectAll(
                        jsonPath("$.id").value(1L),
                        jsonPath("$.payAmount").value(89_000L),
                        jsonPath("$.paymentMethod").value("CARD")
                );
    }

    @Test
    @DisplayName("결제 금액을 입력하지 않은 경우 결제 요청에 실패한다.")
    void when_not_entered_payment_amount_then_failed_pay() throws Exception {
        // Given
        Long userId = 1L;

        // When && Then
        mockMvc.perform(post("/payments/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                           "orderId" : 1,
                           "paymentMethod" : "CARD"
                        }
                        """))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("결제 수단을 입력하지 않은 경우 결제 요청에 실패한다.")
    void when_not_entered_payment_method_then_failed_pay() throws Exception {
        // Given
        Long userId = 1L;

        // When && Then
        mockMvc.perform(post("/payments/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                           "orderId" : 1,
                           "payAmount" : 50000,
                           "paymentMethod" : ""
                        }
                        """))
                .andExpect(status().isBadRequest());
    }
}
