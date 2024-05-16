package com.hanghae.ecommerce.domain.product.event;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.hanghae.ecommerce.domain.order.OrderService;
import com.hanghae.ecommerce.domain.payment.PaymentService;
import com.hanghae.ecommerce.domain.product.StockService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StockEventListener {
	private final PaymentService paymentService;
	private final OrderService orderService;
	private final StockService stockService;

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
