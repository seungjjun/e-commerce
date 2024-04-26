package com.hanghae.ecommerce.api.eventlistener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.hanghae.ecommerce.domain.order.event.OrderCreatedEvent;
import com.hanghae.ecommerce.domain.product.ProductService;

@Component
public class ProductEventHandler {
	private final ProductService productService;

	public ProductEventHandler(ProductService productService) {
		this.productService = productService;
	}

	@EventListener
	public void onProductStockChanged(OrderCreatedEvent event) {
		productService.updateStockQuantity(event.products(), event.orderRequest());
	}
}
