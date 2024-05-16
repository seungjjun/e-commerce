package com.hanghae.ecommerce.domain.outbox;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae.ecommerce.storage.outbox.OutboxEntity;
import com.hanghae.ecommerce.storage.outbox.OutboxStatus;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OutboxEventListener {
	private static final String TOPIC_ORDER = "order-event";

	private final ObjectMapper objectMapper;
	private final OutboxService outboxService;

	@Transactional
	@KafkaListener(id = "outbox-container", topics = TOPIC_ORDER)
	public void orderEventHandler(String message) throws JsonProcessingException {
		OutboxEntity outbox = objectMapper.readValue(message, OutboxEntity.class);

		outboxService.updateStatus(outbox, OutboxStatus.PUBLISHED);
	}
}
