package com.hanghae.ecommerce.api.dto;

import java.time.LocalDateTime;

import com.hanghae.ecommerce.domain.order.Order;

public record OrderPaidResult(
	Long orderId,
	LocalDateTime orderedAt
) {
	public static OrderPaidResult of(Order order) {
		return new OrderPaidResult(order.id(), order.orderedAt());
	}
}
