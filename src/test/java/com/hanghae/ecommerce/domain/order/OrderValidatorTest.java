package com.hanghae.ecommerce.domain.order;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.storage.order.OrderStatus;

class OrderValidatorTest {
	private OrderValidator orderValidator;

	@BeforeEach
	void setUp() {
		orderValidator = new OrderValidator();
	}

	@Test
	@DisplayName("주문 상태가 결제 대기 상태가 아닌 경우 false를 반환한다.")
	void when_order_status_is_canceled_then_fail_pay() {
		// Given
		Order order = Fixtures.order(OrderStatus.CANCELED);

		// When
		boolean result = orderValidator.isOrderStatusWaitingForPay(order);

		// Then
		assertThat(result).isFalse();
	}

	@Test
	@DisplayName("주문 상태가 결제 대기 상태인 경우 true를 반환한다.")
	void when_order_status_is_paid_then_fail_pay() {
		// Given
		Order order = Fixtures.order(OrderStatus.WAITING_FOR_PAY);

		// When
		boolean result = orderValidator.isOrderStatusWaitingForPay(order);

		// Then
		assertThat(result).isTrue();
	}

}
