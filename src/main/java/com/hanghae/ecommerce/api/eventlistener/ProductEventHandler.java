package com.hanghae.ecommerce.api.eventlistener;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.hanghae.ecommerce.domain.order.event.OrderCreatedEvent;
import com.hanghae.ecommerce.domain.product.ProductService;

@Component
public class ProductEventHandler {
	private final ProductService productService;

	public ProductEventHandler(ProductService productService) {
		this.productService = productService;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void onProductStockChanged(OrderCreatedEvent event) {
		productService.updateStockQuantity(event.products(), event.orderRequest());
	}
}
