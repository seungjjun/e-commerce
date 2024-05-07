package com.hanghae.ecommerce.domain.product;

public interface StockRepository {

	void updateStock(Stock decreasedStock);

	Stock findByProductId(Long productId);
}
