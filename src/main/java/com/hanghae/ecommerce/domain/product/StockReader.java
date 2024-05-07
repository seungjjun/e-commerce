package com.hanghae.ecommerce.domain.product;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class StockReader {
	private final StockRepository stockRepository;

	public StockReader(StockRepository stockRepository) {
		this.stockRepository = stockRepository;
	}

	@Transactional
	public Stock readByProductId(Long productId) {
		return stockRepository.findByProductId(productId);
	}
}
