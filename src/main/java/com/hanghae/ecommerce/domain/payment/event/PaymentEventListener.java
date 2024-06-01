package com.hanghae.ecommerce.domain.payment.event;

import com.hanghae.ecommerce.domain.product.ProductService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.hanghae.ecommerce.common.DataPlatformSendService;
import com.hanghae.ecommerce.domain.cart.CartService;
import com.hanghae.ecommerce.domain.order.OrderService;
import com.hanghae.ecommerce.domain.product.StockService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentEventListener {

	private final OrderService orderService;
	private final StockService stockService;
	private final CartService cartService;
	private final DataPlatformSendService sendService;
	private final ProductService productService;

	@Async
	@EventListener
	public void paymentSucceedHandler(PaymentSucceedEvent event) {
		sendService.send(event.order(), event.payment());
		productService.updateStockQuantity(event.order());
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
	public void paymentFailedHandler(PaymentFailedEvent event) {
		orderService.orderFailed(event.order());
		stockService.compensateOrderStock(event.order());
		cartService.compensateCartItems(event.order());
	}
}
