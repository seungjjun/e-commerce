package com.hanghae.ecommerce.storage.product;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;

public interface StockJpaRepository extends JpaRepository<StockEntity, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<StockEntity> findByProductIdIn(List<Long> productIds);
}
