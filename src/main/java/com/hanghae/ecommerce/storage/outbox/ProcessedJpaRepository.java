package com.hanghae.ecommerce.storage.outbox;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessedJpaRepository extends JpaRepository<ProcessedEntity, Long> {
	boolean existsByOrderId(Long orderId);
}
