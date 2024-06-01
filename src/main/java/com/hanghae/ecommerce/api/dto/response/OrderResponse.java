package com.hanghae.ecommerce.api.dto.response;

import com.hanghae.ecommerce.domain.order.Order;

public record OrderResponse(Long orderId) {

	public static OrderResponse from(Order order) {
		return new OrderResponse(order.id());
	}
}
