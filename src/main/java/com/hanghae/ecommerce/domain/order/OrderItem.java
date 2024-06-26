package com.hanghae.ecommerce.domain.order;

public record OrderItem(
	Long id,
	Long orderId,
	Long productId,
	String productName,
	Long unitPrice,
	Long totalPrice,
	Long quantity,
	String status
) {
}
