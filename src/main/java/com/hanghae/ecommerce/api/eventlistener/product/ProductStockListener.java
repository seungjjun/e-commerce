package com.hanghae.ecommerce.api.eventlistener.product;

import com.hanghae.ecommerce.domain.order.event.OrderCreatedEvent;
import com.hanghae.ecommerce.domain.order.event.OrderFailedEvent;
import com.hanghae.ecommerce.domain.product.Stock;
import com.hanghae.ecommerce.domain.product.StockService;
import com.hanghae.ecommerce.domain.product.event.DecreasedStockEvent;
import com.hanghae.ecommerce.storage.order.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Component
@Slf4j
public class ProductStockListener {
	private final StockService stockService;
	private final ApplicationEventPublisher applicationEventPublisher;

	public ProductStockListener(StockService stockService, ApplicationEventPublisher applicationEventPublisher) {
		this.stockService = stockService;
		this.applicationEventPublisher = applicationEventPublisher;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void onProductStockUpdate(OrderCreatedEvent event) {
		List<Stock> stocks = stockService.getStocksByProductIds(event.products());
		try {
			List<Stock> decreasedStock = stockService.decreaseProductStock(stocks, event.orderRequest().products());
			applicationEventPublisher.publishEvent(new DecreasedStockEvent(
					event.user(),
					event.order(),
					event.products(),
					event.orderRequest(),
					decreasedStock)
			);
		} catch (Exception e) {
			applicationEventPublisher.publishEvent(new OrderFailedEvent(
					event.order(),
					OrderStatus.OUT_OF_STOCK,
					List.of(),
					event.orderRequest()));
		}
	}
}
