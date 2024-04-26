package com.hanghae.ecommerce.storage.product;

import java.util.List;

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
	public List<Stock> findByProductIdIn(List<Long> productIds) {
		return stockJpaRepository.findByProductIdIn(productIds)
			.stream().map(StockEntity::toStock)
			.toList();
	}

	@Override
	public void updateStock(Stock stock) {
		StockEntity stockEntity = stockJpaRepository.findById(stock.id())
			.orElseThrow(() -> new EntityNotFoundException("재고 정보를 찾지 못했습니다. - id: " + stock.id()));
		stockEntity.updateStock(stock.stockQuantity());
	}
}
