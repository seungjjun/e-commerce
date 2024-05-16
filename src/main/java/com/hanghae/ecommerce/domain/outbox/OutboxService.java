package com.hanghae.ecommerce.domain.outbox;

import com.hanghae.ecommerce.storage.order.OrderStatus;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.storage.outbox.OutboxEntity;
import com.hanghae.ecommerce.storage.outbox.OutboxStatus;
import com.hanghae.ecommerce.storage.outbox.OutboxType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OutboxService {
	private static final String TOPIC_ORDER = "order-event";

	private final OutboxRepository outboxRepository;
	private final ObjectMapper objectMapper;

	@Transactional
	public void recordOrderOutbox(Order order) throws JsonProcessingException {
		String payload = objectMapper.writeValueAsString(order);
		OutboxEntity outbox = new OutboxEntity(order.id(), OutboxType.ORDER, OutboxStatus.INIT, TOPIC_ORDER, payload);
		outboxRepository.record(outbox);
	}

	@Transactional
	public OutboxEntity getOrderOutbox(Long orderId) {
		return outboxRepository.findByOrderId(orderId)
			.orElseThrow(() -> new RuntimeException("주문 생성 이벤트가 발행되지 않았습니다."));
	}

	public void updateStatus(OutboxEntity outbox, OutboxStatus status) {
		outbox.changeStatus(status);
		outboxRepository.record(outbox);
	}

	public List<OutboxEntity> getInitOutbox() {
		return outboxRepository.findByStatus(OutboxStatus.INIT);
	}
}
