package com.hanghae.ecommerce.domain.order.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderEventPublisher {
	private final ApplicationEventPublisher applicationEventPublisher;

	public void publishEvent(OrderCreatedEvent event) {
		applicationEventPublisher.publishEvent(event);
	}
}
