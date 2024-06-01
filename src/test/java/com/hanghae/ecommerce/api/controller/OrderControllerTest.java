package com.hanghae.ecommerce.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.storage.order.OrderStatus;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.hanghae.ecommerce.api.dto.OrderPaidResult;
import com.hanghae.ecommerce.api.dto.request.Receiver;
import com.hanghae.ecommerce.application.order.OrderUseCase;

@WebMvcTest(OrderController.class)
class OrderControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OrderUseCase orderUseCase;

	@Test
	@DisplayName("주문 생성 요청")
	void post_order() throws Exception {
		// Given
		Long userId = 1L;
		Order order = Fixtures.order(OrderStatus.READY);

		given(orderUseCase.order(anyLong(), any())).willReturn(order);

		// When && Then
		mockMvc.perform(post("/orders/" + userId)
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{
						"receiver": {
						"name": "홍길동",
						"address": "서울특별시 송파구",
						"phoneNumber": "01012345678"
					},
					"products" : [
						{
							"id": 1,
							"quantity": 1
						}
					],
					"paymentAmount": 58000,
					"paymentMethod" : "CARD"
					}
					"""))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.orderId").value(1L));
	}

	@Test
	@DisplayName("받는 사람 정보를 입력하지 않은 경우 주문 요청에 실패한다.")
	void when_not_entered_receiver_then_failed_order() throws Exception {
		// Given
		Long userId = 1L;

		// When && Then
		mockMvc.perform(post("/orders/" + userId)
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
							"receiver":{
							"name" :"",
							"address":"",
							"phoneNumber":""
						},
						"paymentAmount":58000,
						"paymentMethod" :"CARD"
						}
					"""))
			.andExpect(status().isBadRequest());

		verify(orderUseCase, never()).order(anyLong(), any());
	}

	@Test
	@DisplayName("결제 금액을 입력하지 않은 경우 주문 요청에 실패한다.")
	void when_not_entered_pay_amount_then_failed_order() throws Exception {
		// Given
		Long userId = 1L;

		// When && Then
		mockMvc.perform(post("/orders/" + userId)
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{
						"receiver":{
						"name":"홍길동",
						"address":"서울특별시 송파구",
						"phoneNumber":"01012345678"
					},
					"paymentMethod" :"CARD"
					}
					"""))
			.andExpect(status().isBadRequest());

		verify(orderUseCase, never()).order(anyLong(), any());
	}

	@Test
	@DisplayName("결제 수단을 입력하지 않은 경우 주문 요청에 실패한다.")
	void when_not_entered_pay_method_then_failed_order() throws Exception {
		// Given
		Long userId = 1L;

		// When && Then
		mockMvc.perform(post("/orders/" + userId)
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{
						"receiver":{
						"name":"홍길동",
						"address":"서울특별시 송파구",
						"phoneNumber":"01012345678"
					},
					"paymentAmount":58000
					}
					"""))
			.andExpect(status().isBadRequest());

		verify(orderUseCase, never()).order(anyLong(), any());
	}
}
