package com.hanghae.ecommerce.domain.outbox;

import com.hanghae.ecommerce.storage.outbox.ProcessedEntity;

public interface ProcessedRepository {
	void save(ProcessedEntity processedEntity);

	boolean existsByOrderId(Long orderId);
}
