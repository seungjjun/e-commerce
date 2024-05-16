package com.hanghae.ecommerce.domain.product;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hanghae.ecommerce.domain.cart.CartItemRemover;
import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.order.OrderItem;
import com.hanghae.ecommerce.domain.order.OrderReader;
import com.hanghae.ecommerce.domain.order.OrderUpdater;
import com.hanghae.ecommerce.domain.product.event.StockDecreaseFailedEvent;
import com.hanghae.ecommerce.domain.product.event.StockDecreaseSucceedEvent;
import com.hanghae.ecommerce.domain.product.event.StockEventPublisher;
import com.hanghae.ecommerce.storage.order.OrderItemStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockService {
	private final StockReader stockReader;
	private final StockValidator stockValidator;
	private final StockUpdator stockUpdator;
	private final OrderReader orderReader;
	private final OrderUpdater orderUpdater;
	private final CartItemRemover cartItemRemover;
	private final StockEventPublisher eventPublisher;

	@Transactional
	public void updateStockQuantityForOrder(Order order) {
		try {
			for (OrderItem item : order.items()) {
				decreaseProductStock(item);
				orderUpdater.changeItemStatus(item, OrderItemStatus.SUCCESS);
			}
			cartItemRemover.resetCart(order.userId());
			eventPublisher.success(new StockDecreaseSucceedEvent(order));
		} catch (Exception e) {
			eventPublisher.fail(new StockDecreaseFailedEvent(order));
			throw new RuntimeException(e.getMessage());
		}
	}

	@Transactional
	public void decreaseProductStock(OrderItem item) {
		Stock stock = stockReader.readByProductId(item.productId());
		stockValidator.checkProductStockQuantityForOrder(stock, item);
		stockUpdator.decreaseStock(stock, item);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void compensateOrderStock(Order order) {
		Order foundOrder = orderReader.read(order.id());
		for (OrderItem item : foundOrder.items()) {
			if (item.status().equals(OrderItemStatus.SUCCESS.toString())) {
				Stock stock = stockReader.readByProductId(item.productId());
				stockUpdator.increaseStock(stock, item);
			}
		}
	}
}
