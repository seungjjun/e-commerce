package com.hanghae.ecommerce.storage.product;

import org.springframework.stereotype.Repository;

import com.hanghae.ecommerce.domain.product.Stock;
import com.hanghae.ecommerce.domain.product.StockRepository;

import jakarta.persistence.EntityNotFoundException;

@Repository
public class StockCoreRepository implements StockRepository {
	private final StockJpaRepository stockJpaRepository;

	public StockCoreRepository(StockJpaRepository stockJpaRepository) {
		this.stockJpaRepository = stockJpaRepository;
	}

	@Override
	public void updateStock(Stock stock) {
		StockEntity stockEntity = stockJpaRepository.findById(stock.id())
			.orElseThrow(() -> new EntityNotFoundException("재고 정보를 찾지 못했습니다. - id: " + stock.id()));
		stockEntity.updateStock(stock.stockQuantity());
	}

	@Override
	public Stock findByProductId(Long productId) {
		return stockJpaRepository.findByProductId(productId)
			.orElseThrow(() -> new EntityNotFoundException("재고 정보를 찾지 못했습니다. - id: " + productId))
			.toStock();
	}
}
