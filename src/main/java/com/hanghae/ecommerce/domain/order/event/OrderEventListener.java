package com.hanghae.ecommerce.domain.order.event;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.order.adaptor.OrderProducer;
import com.hanghae.ecommerce.domain.outbox.OutboxService;
import com.hanghae.ecommerce.storage.outbox.OutboxEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventListener {

	private final OutboxService outboxService;
	private final OrderProducer orderProducer;

	@Async
	@TransactionalEventListener
	public void orderCreatedHandler(OrderCreatedEvent event) throws JsonProcessingException {
		Order order = event.order();

		OutboxEntity outbox = outboxService.getOrderOutbox(order.id());
		orderProducer.sendOrder(outbox);
	}
}
