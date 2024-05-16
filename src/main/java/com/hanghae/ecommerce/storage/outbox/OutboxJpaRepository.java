package com.hanghae.ecommerce.storage.outbox;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxJpaRepository extends JpaRepository<OutboxEntity, Long> {
	Optional<OutboxEntity> findByAggregateId(Long orderId);

	List<OutboxEntity> findByStatus(OutboxStatus status);
}
