package com.hanghae.ecommerce.domain.outbox;

import org.springframework.stereotype.Service;

import com.hanghae.ecommerce.storage.outbox.ProcessedEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProcessedService {
	private final ProcessedRepository processedRepository;

	public boolean checkExistedProcessed(Long orderId) {
		return processedRepository.existsByOrderId(orderId);
	}

	public void record(Long orderId) {
		ProcessedEntity processedEntity = new ProcessedEntity(orderId);
		processedRepository.save(processedEntity);
	}
}
