package com.hanghae.ecommerce.domain.product;

import com.hanghae.ecommerce.api.error.InsufficientStockException;

public record Product(
	Long id,
	String name,
	Long price,
	String description,
	Long stockQuantity
) {
	public Long orderTotalPrice(Long quantity) {
		return price * quantity;
	}

	public Product updateStock(Long stockQuantity) {
		return new Product(id, name, price, description, stockQuantity);
	}

	public Product decreaseStock(Long stockQuantity) {
		return new Product(id, name, price, description, this.stockQuantity - stockQuantity);
	}

	public void isEnoughStockQuantity(Long quantity) {
		if (this.stockQuantity < quantity) {
			throw new InsufficientStockException("상품의 수량이 부족합니다.");
		}
	}
}
