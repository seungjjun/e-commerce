package com.hanghae.ecommerce.api.eventlistener.order;

import com.hanghae.ecommerce.domain.order.OrderService;
import com.hanghae.ecommerce.domain.order.event.OrderFailedEvent;
import com.hanghae.ecommerce.domain.product.StockService;
import com.hanghae.ecommerce.storage.order.OrderStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class OrderFailedListener {

	private final OrderService orderService;
	private final StockService stockService;

	public OrderFailedListener(OrderService orderService, StockService stockService) {
		this.orderService = orderService;
		this.stockService = stockService;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void onOrderFailed(OrderFailedEvent event) {
		orderService.updateOrderStatus(event.order(), event.orderStatus());
		if (event.orderStatus().equals(OrderStatus.PAY_FAILED)) {
			stockService.compensateStock(event.stocks(), event.orderRequest());
		}
	}
}
