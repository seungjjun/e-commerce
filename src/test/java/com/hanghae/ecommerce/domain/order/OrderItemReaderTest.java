package com.hanghae.ecommerce.domain.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.hanghae.ecommerce.Fixtures;

class OrderItemReaderTest {
	private OrderItemReader orderItemReader;

	private OrderItemRepository orderItemRepository;

	@BeforeEach
	void setUp() {
		orderItemRepository = mock(OrderItemRepository.class);

		orderItemReader = new OrderItemReader(orderItemRepository);
	}

	@Test
	@DisplayName("주문 Id 기반의 주문 아이템 목록을 조회한다.")
	void read_order_items_by_order_id() {
		// Given
		Long orderId = 1L;

		List<OrderItem> orderItemList = List.of(
			Fixtures.orderItem(orderId, 1L)
		);

		given(orderItemRepository.findAllByOrderId(any())).willReturn(orderItemList);
		// When
		List<OrderItem> foundOrderItemList = orderItemReader.readAllByOrderId(orderId);

		// Then
		assertThat(foundOrderItemList.size()).isEqualTo(1);
		assertThat(foundOrderItemList.getFirst().productName()).isEqualTo("후드티");
	}
}
