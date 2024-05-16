package com.hanghae.ecommerce.domain.order.adaptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hanghae.ecommerce.storage.outbox.OutboxEntity;

public interface OrderProducer {
	void sendOrder(OutboxEntity outbox) throws JsonProcessingException;
}
