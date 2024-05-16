package com.hanghae.ecommerce.domain.outbox;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hanghae.ecommerce.domain.order.adaptor.OrderProducer;
import com.hanghae.ecommerce.storage.outbox.OutboxEntity;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OutboxScheduler {

	private final OutboxService outboxService;
	private final OrderProducer orderProducer;

	@Scheduled(fixedRate = 600000)
	public void processFailedOutboxMessages() throws JsonProcessingException {
		List<OutboxEntity> initOutboxMessages = outboxService.getInitOutbox();
		for (OutboxEntity outbox : initOutboxMessages) {
			orderProducer.sendOrder(outbox);
		}
	}
}
