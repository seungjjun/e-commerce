package com.hanghae.ecommerce.api.controller;

import com.hanghae.ecommerce.domain.user.UserPointService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserPointService userPointService;

    @Test
    @DisplayName("포인트 충전 성공")
    void succeed_charge_point() throws Exception {
        // Given
        Long userId = 1L;
        Long balance = 500L;

        given(userPointService.chargePoint(anyLong(), anyLong())).willReturn(balance);

        // When && Then
        mockMvc.perform(patch("/accounts/" + userId + "/charge")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "amount" : 500
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(500));
    }

    @Test
    @DisplayName("포인트 충전 실패 (음수 입력 시)")
    void when_charge_point_negative_failed_charge_point() throws Exception {
        // Given
        Long userId = 1L;

        // When && Then
        mockMvc.perform(patch("/accounts/" + userId + "/charge")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "amount": -1000
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("포인트 충전 실패 (0 입력 시)")
    void when_charge_point_zero_then_failed_charge_point() throws Exception {
        // Given
        Long userId = 1L;

        // When && Then
        mockMvc.perform(patch("/accounts/" + userId + "/charge")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "amount": 0
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("잔액 조회 성공")
    void get_user_point_balance() throws Exception {
        // Given
        Long userId = 1L;
        Long balance = 5000L;

        given(userPointService.getPoint(userId)).willReturn(balance);

        // When && Then
        mockMvc.perform(get("/accounts/" + userId + "/balance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(5000L));
    }
}
