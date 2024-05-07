package com.hanghae.ecommerce.domain.product;

import org.springframework.stereotype.Component;

import com.hanghae.ecommerce.domain.order.OrderItem;

@Component
public class StockValidator {
	public void checkProductStockQuantityForOrder(Stock stock, OrderItem item) {
		stock.isEnoughProductStockQuantityForOrder(item.quantity());
	}
}
