package com.hanghae.ecommerce.domain.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
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
