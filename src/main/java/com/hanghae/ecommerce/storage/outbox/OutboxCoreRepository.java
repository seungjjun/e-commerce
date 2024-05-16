package com.hanghae.ecommerce.storage.outbox;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

import com.hanghae.ecommerce.domain.outbox.OutboxRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OutboxCoreRepository implements OutboxRepository {
	private final OutboxJpaRepository outboxJpaRepository;

	@Override
	public OutboxEntity record(OutboxEntity outbox) {
		return outboxJpaRepository.save(outbox);
	}

	@Override
	public Optional<OutboxEntity> findByOrderId(Long orderId) {
		return outboxJpaRepository.findByAggregateId(orderId);
	}

	@Override
	public List<OutboxEntity> findByStatus(OutboxStatus outboxStatus) {
		return outboxJpaRepository.findByStatus(outboxStatus);
	}
}
