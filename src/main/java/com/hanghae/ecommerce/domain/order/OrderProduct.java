package com.hanghae.ecommerce.domain.order;

import com.hanghae.ecommerce.domain.product.Product;

public record OrderProduct(
	Long productId,
	String productName,
	Long unitPrice,
	Long totalPrice,
	Long quantity
) {
	public static OrderProduct of(Product product, Long quantity) {
		return new OrderProduct(
			product.id(),
			product.name(),
			product.price(),
			product.orderTotalPrice(quantity),
			quantity);
	}
}
