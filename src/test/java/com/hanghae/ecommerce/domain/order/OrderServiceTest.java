package com.hanghae.ecommerce.domain.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.api.dto.request.Receiver;
import com.hanghae.ecommerce.domain.orderitem.OrderItemAppender;
import com.hanghae.ecommerce.domain.product.Product;
import com.hanghae.ecommerce.domain.user.User;
import com.hanghae.ecommerce.storage.order.OrderStatus;

class OrderServiceTest {
	private OrderService orderService;
	private OrderItemAppender orderItemAppender;
	private OrderProcessor orderProcessor;
	private OrderUpdater orderUpdater;

	private User user;
	private OrderRequest request;

	@BeforeEach
	void setUp() {
		orderItemAppender = mock(OrderItemAppender.class);
		orderProcessor = mock(OrderProcessor.class);
		orderUpdater = mock(OrderUpdater.class);

		orderService = new OrderService(orderItemAppender, orderProcessor, orderUpdater);

		user = Fixtures.user(1L);
		request = new OrderRequest(
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
	}

	@Test
	@DisplayName("주문 생성 성공 - 주문 상태 waiting for pay")
	void when_succeed_order_then_order_status_is_complete() {
		// Given
		List<Product> products = List.of();
		Order readyOrder = Fixtures.order(OrderStatus.READY);
		Order waitingForPayOrder = Fixtures.order(OrderStatus.WAITING_FOR_PAY);

		given(orderProcessor.order(any(), any())).willReturn(readyOrder);
		given(orderUpdater.changeStatus(any(), any())).willReturn(waitingForPayOrder);

		// When
		Order order = orderService.order(user, products, request);

		// Then
		assertThat(order).isNotNull();
		assertThat(order.payAmount()).isEqualTo(89_000L);
		assertThat(order.orderStatus()).isEqualTo("WAITING FOR PAY");
		verify(orderUpdater, atLeastOnce()).changeStatus(any(), any());
	}
}
