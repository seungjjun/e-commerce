package com.hanghae.ecommerce.storage.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StockJpaRepository extends JpaRepository<StockEntity, Long> {
	List<StockEntity> findByProductIdIn(List<Long> productIds);
}
