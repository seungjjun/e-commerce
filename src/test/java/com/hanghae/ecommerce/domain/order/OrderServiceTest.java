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
import com.hanghae.ecommerce.domain.cart.Cart;
import com.hanghae.ecommerce.domain.product.Product;
import com.hanghae.ecommerce.domain.user.User;
import com.hanghae.ecommerce.storage.order.OrderItemStatus;
import com.hanghae.ecommerce.storage.order.OrderStatus;

class OrderServiceTest {
	private OrderService orderService;
	private OrderAppender orderAppender;
	private OrderUpdater orderUpdater;
	private OrderReader orderReader;

	private User user;
	private OrderRequest request;

	@BeforeEach
	void setUp() {
		orderReader = mock(OrderReader.class);
		orderAppender = mock(OrderAppender.class);
		orderUpdater = mock(OrderUpdater.class);

		orderService = new OrderService(orderReader, orderAppender, orderUpdater);

		user = Fixtures.user(1L);
		request = new OrderRequest(
			new Receiver(
				user.name(),
				user.address(),
				user.phoneNumber()
			),
			50_000L,
			"CARD"
		);
	}

	@Test
	@DisplayName("주문 생성 성공")
	void create_order() {
		// Given
		Order readyOrder = Fixtures.order(OrderStatus.READY);
		Cart cart = new Cart(1L, user.id(), List.of());

		given(orderAppender.append(any(), any(), any())).willReturn(readyOrder);

		// When
		Order order = orderService.order(user.id(), cart, request);

		// Then
		assertThat(order).isNotNull();
		assertThat(order.payAmount()).isEqualTo(89_000L);
		assertThat(order.orderStatus()).isEqualTo("READY");
	}

	@Test
	@DisplayName("주문 아이템 상태 변경")
	void update_order_item() {
		// Given
		Product product = Fixtures.product("후드티");
		OrderItem item = new OrderItem(
			1L, 1L,
			product.id(), product.name(),
			product.price(), product.orderTotalPrice(5L),
			5L, OrderItemStatus.CREATED.toString());

		// When
		orderService.updateItemStatus(item, OrderItemStatus.SUCCESS);

		// Then
		verify(orderUpdater, atLeastOnce()).changeItemStatus(any(), any());
	}

	@Test
	@DisplayName("주문 실패 시 주문 상태 변경")
	void when_order_failed_then_order_status_is_failed() {
		// Given
		Order order = Fixtures.order(OrderStatus.READY);

		// When
		orderService.orderFailed(order);

		// Then
		verify(orderUpdater, atLeastOnce()).changeStatus(any(), any());
	}
}
