package com.hanghae.ecommerce.storage.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import jakarta.persistence.LockModeType;

public interface StockJpaRepository extends JpaRepository<StockEntity, Long> {
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	List<StockEntity> findByProductIdIn(List<Long> productIds);
}
