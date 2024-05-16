package com.hanghae.ecommerce.domain.payment.event;

import com.hanghae.ecommerce.domain.cart.CartService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.hanghae.ecommerce.common.DataPlatformSendService;
import com.hanghae.ecommerce.domain.order.OrderService;
import com.hanghae.ecommerce.domain.product.StockService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentEventListener {

	private final OrderService orderService;
	private final StockService stockService;
	private final CartService cartService;
	private final DataPlatformSendService sendService;

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void paymentSucceedHandler(PaymentSucceedEvent event) {
		sendService.send(event.order(), event.payment());
	}

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void paymentFailedHandler(PaymentFailedEvent event) {
		orderService.orderFailed(event.order());
		stockService.compensateOrderStock(event.order());
		cartService.compensateCartItems(event.order());
	}
}
