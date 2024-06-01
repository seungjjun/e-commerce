package com.hanghae.ecommerce.domain.payment.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentEventPublisher {
	private final ApplicationEventPublisher applicationEventPublisher;

	public void success(PaymentSucceedEvent event) {
		applicationEventPublisher.publishEvent(event);
	}

	public void fail(PaymentFailedEvent event) {
		applicationEventPublisher.publishEvent(event);
	}
}
