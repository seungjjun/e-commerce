package com.hanghae.ecommerce.domain.order;

import java.time.LocalDateTime;

import com.hanghae.ecommerce.storage.order.OrderStatus;

public record Order(
	Long id,
	Long userId,
	Long payAmount,
	String receiverName,
	String address,
	String phoneNumber,
	String orderStatus,
	LocalDateTime orderedAt
) {
	public Order changeStatus(OrderStatus orderStatus) {
		return new Order(id, userId, payAmount, receiverName, address, phoneNumber, orderStatus.toString(), orderedAt);
	}
}
