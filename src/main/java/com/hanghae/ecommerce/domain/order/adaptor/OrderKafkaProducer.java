package com.hanghae.ecommerce.domain.order.adaptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.hanghae.ecommerce.storage.outbox.OutboxEntity;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderKafkaProducer implements OrderProducer {

	private final KafkaTemplate<String, String> kafkaTemplate;
	private final ObjectMapper objectMapper;

	@Override
	public void sendOrder(OutboxEntity outbox) throws JsonProcessingException {
		String message = objectMapper.writeValueAsString(outbox);
		kafkaTemplate.send(outbox.getTopic(), message);
	}
}
