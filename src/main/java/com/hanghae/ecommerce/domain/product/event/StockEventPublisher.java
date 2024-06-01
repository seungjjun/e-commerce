package com.hanghae.ecommerce.domain.product.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class StockEventPublisher {
	private final ApplicationEventPublisher applicationEventPublisher;

	public StockEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

	public void success(StockDecreaseSucceedEvent event) {
		applicationEventPublisher.publishEvent(event);
	}

	public void fail(StockDecreaseFailedEvent event) {
		applicationEventPublisher.publishEvent(event);
	}
}
