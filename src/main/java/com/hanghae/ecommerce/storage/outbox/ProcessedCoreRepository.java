package com.hanghae.ecommerce.storage.outbox;

import org.springframework.stereotype.Repository;

import com.hanghae.ecommerce.domain.outbox.ProcessedRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProcessedCoreRepository implements ProcessedRepository {
	private final ProcessedJpaRepository processedJpaRepository;

	@Override
	public void save(ProcessedEntity processedEntity) {
		processedJpaRepository.save(processedEntity);
	}

	@Override
	public boolean existsByOrderId(Long orderId) {
		return processedJpaRepository.existsByOrderId(orderId);
	}
}
