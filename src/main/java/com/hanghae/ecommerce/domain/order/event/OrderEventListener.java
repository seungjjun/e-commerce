package com.hanghae.ecommerce.domain.order.event;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.hanghae.ecommerce.domain.product.StockService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderEventListener {
	private final StockService stockService;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void orderCreatedHandler(OrderCreatedEvent event) {
		stockService.updateStockQuantityForOrder(event.order());
	}
}
