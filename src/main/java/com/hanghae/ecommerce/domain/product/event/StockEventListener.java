package com.hanghae.ecommerce.domain.product.event;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.order.OrderService;
import com.hanghae.ecommerce.domain.outbox.ProcessedService;
import com.hanghae.ecommerce.domain.payment.PaymentService;
import com.hanghae.ecommerce.domain.product.StockService;
import com.hanghae.ecommerce.storage.outbox.OutboxEntity;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StockEventListener {
	private static final String TOPIC_ORDER = "order-event";

	private final PaymentService paymentService;
	private final OrderService orderService;
	private final StockService stockService;
	private final ProcessedService processedService;
	private final ObjectMapper objectMapper;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@KafkaListener(id = "stock-container", topics = TOPIC_ORDER)
	public void orderEventHandler(String message) throws JsonProcessingException {
		OutboxEntity outbox = objectMapper.readValue(message, OutboxEntity.class);

		Long orderId = outbox.getAggregateId();
		Order order = orderService.getOrder(orderId);

		// 이미 처리된 이벤트인지 확인
		if (!processedService.checkExistedProcessed(orderId)) {
			stockService.updateStockQuantityForOrder(order);
			// 이벤트 처리 기록
			processedService.record(orderId);
		}
	}

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void stockDecreaseSucceedHandler(StockDecreaseSucceedEvent event) {
		paymentService.pay(event.order());
	}

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void stockDecreaseFailedHandler(StockDecreaseFailedEvent event) {
		orderService.orderFailed(event.order());
		stockService.compensateOrderStock(event.order());
	}
}
