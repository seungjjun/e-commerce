package com.hanghae.ecommerce.domain.payment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.order.OrderUpdater;
import com.hanghae.ecommerce.domain.payment.event.PaymentEventPublisher;
import com.hanghae.ecommerce.domain.payment.event.PaymentFailedEvent;
import com.hanghae.ecommerce.domain.payment.event.PaymentSucceedEvent;
import com.hanghae.ecommerce.domain.user.UserPointManager;
import com.hanghae.ecommerce.storage.order.OrderStatus;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

	private final OrderUpdater orderUpdater;
	private final PaymentAppender paymentAppender;
	private final UserPointManager userPointManager;
	private final PaymentEventPublisher paymentEventPublisher;

	@Transactional
	public void pay(Order order) {
		try {
			userPointManager.usePoint(order.userId(), order.payAmount());
			orderUpdater.changeStatus(order, OrderStatus.PAID);
			Payment payment = paymentAppender.create(order, order.payAmount(), order.paymentMethod());
			paymentEventPublisher.success(new PaymentSucceedEvent(order, payment));
		} catch (Exception e) {
			paymentEventPublisher.fail(new PaymentFailedEvent(order));
		}
	}
}
