package com.hanghae.ecommerce.domain.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.api.dto.request.Receiver;
import com.hanghae.ecommerce.domain.user.User;
import com.hanghae.ecommerce.storage.order.OrderStatus;

class OrderProcessorTest {
	private OrderProcessor orderProcessor;

	private OrderRepository orderRepository;

	@BeforeEach
	void setUp() {
		orderRepository = mock(OrderRepository.class);

		orderProcessor = new OrderProcessor(orderRepository);
	}

	@Test
	@DisplayName("주문 생성")
	void createOrder() {
		// Given
		User user = Fixtures.user(1L);
		OrderRequest orderRequest = new OrderRequest(
			new Receiver(
				user.name(),
				user.address(),
				user.phoneNumber()
			),
			List.of(
				new OrderRequest.ProductOrderRequest(1L, 1L)
			),
			50_000L,
			"CARD"
		);
		Order order = new Order(
			1L,
			user.id(),
			orderRequest.paymentAmount(),
			orderRequest.receiver().name(),
			orderRequest.receiver().address(),
			orderRequest.receiver().phoneNumber(),
			OrderStatus.READY.toString(),
			LocalDateTime.now());

		given(orderRepository.order(any(), any())).willReturn(order);

		// When
		Order createdOrder = orderProcessor.order(user, orderRequest);

		// Then
		assertThat(createdOrder.orderStatus()).isEqualTo("READY");
		assertThat(createdOrder.payAmount()).isEqualTo(50_000L);
	}
}
