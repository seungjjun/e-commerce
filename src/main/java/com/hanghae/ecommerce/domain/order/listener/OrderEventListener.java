package com.hanghae.ecommerce.domain.order.listener;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.hanghae.ecommerce.domain.order.OrderItem;
import com.hanghae.ecommerce.domain.order.event.OrderCreatedEvent;
import com.hanghae.ecommerce.domain.product.Product;
import com.hanghae.ecommerce.domain.product.ProductService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderEventListener {

	private final ProductService productService;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void onProductStockChanged(OrderCreatedEvent event) {
		List<Product> products = productService.getProductsByIds(
			event.order().items().stream()
				.map(OrderItem::productId)
				.toList()
		);

		productService.updateStockQuantity(products, event.order());
	}
}
