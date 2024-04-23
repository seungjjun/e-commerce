package com.hanghae.ecommerce.api.eventlistener.payment;

import com.hanghae.ecommerce.domain.order.event.OrderFailedEvent;
import com.hanghae.ecommerce.domain.order.event.OrderPaidEvent;
import com.hanghae.ecommerce.domain.payment.Payment;
import com.hanghae.ecommerce.domain.payment.PaymentService;
import com.hanghae.ecommerce.domain.product.event.DecreasedStockEvent;
import com.hanghae.ecommerce.storage.order.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
public class PaymentListener {
	private final PaymentService paymentService;
	private final ApplicationEventPublisher applicationEventPublisher;

	public PaymentListener(PaymentService paymentService, ApplicationEventPublisher applicationEventPublisher) {
		this.paymentService = paymentService;
		this.applicationEventPublisher = applicationEventPublisher;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void onOrderPayment(DecreasedStockEvent event) {
		try {
			Payment payment = paymentService.pay(event.user(), event.order(), event.orderRequests());
			applicationEventPublisher.publishEvent(new OrderPaidEvent(
					event.order(),
					payment,
					event.products(),
					event.orderRequests()
			));
		} catch (Exception e) {
			applicationEventPublisher.publishEvent(new OrderFailedEvent(
					event.order(),
					OrderStatus.PAY_FAILED,
					event.decreasedStock(),
					event.orderRequests()));
		}
	}
}
