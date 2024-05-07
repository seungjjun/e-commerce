package com.hanghae.ecommerce.domain.product;

public record Stock(
	Long id,
	Long productId,
	Long stockQuantity
) {
	public void isEnoughProductStockQuantityForOrder(Long orderQuantity) {
		if (stockQuantity < orderQuantity) {
			throw new IllegalArgumentException(id + " 상품의 재고가 부족합니다.");
		}
	}

	public Stock decreaseQuantity(Long quantity) {
		return new Stock(id, productId, stockQuantity - quantity);
	}

	public Stock increaseQuantity(Long quantity) {
		return new Stock(id, productId, stockQuantity + quantity);
	}
}
