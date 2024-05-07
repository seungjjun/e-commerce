package com.hanghae.ecommerce.storage.product;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import jakarta.persistence.LockModeType;

public interface StockJpaRepository extends JpaRepository<StockEntity, Long> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Optional<StockEntity> findByProductId(Long productId);
}
