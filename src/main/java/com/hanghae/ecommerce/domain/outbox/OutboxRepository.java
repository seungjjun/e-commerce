package com.hanghae.ecommerce.domain.outbox;

import com.hanghae.ecommerce.storage.outbox.OutboxEntity;
import com.hanghae.ecommerce.storage.outbox.OutboxStatus;
import java.util.List;
import java.util.Optional;

public interface OutboxRepository {
	OutboxEntity record(OutboxEntity outbox);

	Optional<OutboxEntity> findByOrderId(Long orderId);

	List<OutboxEntity> findByStatus(OutboxStatus outboxStatus);
}
