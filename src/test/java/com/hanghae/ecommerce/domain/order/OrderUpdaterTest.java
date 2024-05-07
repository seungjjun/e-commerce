package com.hanghae.ecommerce.domain.order;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.storage.order.OrderItemStatus;
import com.hanghae.ecommerce.storage.order.OrderStatus;

class OrderUpdaterTest {
	private OrderUpdater orderUpdater;

	private OrderRepository orderRepository;
	private OrderItemRepository orderItemRepository;

	@BeforeEach
	void setUp() {
		orderRepository = mock(OrderRepository.class);
		orderItemRepository = mock(OrderItemRepository.class);

		orderUpdater = new OrderUpdater(orderRepository, orderItemRepository);
	}

	@Test
	@DisplayName("주문 상태를 변경한다.")
	void change_order_status() {
		// Given
		Order readyOrder = Fixtures.order(OrderStatus.READY);

		// When
		orderUpdater.changeStatus(readyOrder, OrderStatus.CANCELED);

		// Then
		verify(orderRepository, atLeastOnce()).updateStatus(any(), any());
	}

	@Test
	@DisplayName("주문 아이템의 상태를 변경한다.")
	void change_order_item_status() {
		// Given
		OrderItem item = Fixtures.orderItem(1L, 1L);

		// When
		orderUpdater.changeItemStatus(item, OrderItemStatus.SUCCESS);

		// Then
		verify(orderItemRepository, atLeastOnce()).updateStatus(any(), any());
	}

}
