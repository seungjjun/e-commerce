package com.hanghae.ecommerce.domain.payment.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentEventPublisher {
	private final ApplicationEventPublisher applicationEventPublisher;

	public void success(PaymentSucceedEvent event) {
		applicationEventPublisher.publishEvent(event);
	}

	public void fail(PaymentFailedEvent event) {
		applicationEventPublisher.publishEvent(event);
	}
}
