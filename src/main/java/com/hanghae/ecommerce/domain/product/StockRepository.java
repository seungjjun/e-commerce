package com.hanghae.ecommerce.domain.product;

import java.util.List;

public interface StockRepository {
	List<Stock> findByProductIdIn(List<Long> productIds);

	void updateStock(Stock stock);
}
