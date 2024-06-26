package com.hanghae.ecommerce.domain.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.hanghae.ecommerce.domain.order.OrderItem;

@Component
@Slf4j
public class StockUpdator {
	private final StockRepository stockRepository;

	public StockUpdator(StockRepository stockRepository) {
		this.stockRepository = stockRepository;
	}

	public void decreaseStock(Stock stock, OrderItem item) {
		Stock decreasedStock = stock.decreaseQuantity(item.quantity());
		stockRepository.updateStock(decreasedStock);
	}

	public void increaseStock(Stock stock, OrderItem item) {
		Stock increasedStock = stock.increaseQuantity(item.quantity());
		stockRepository.updateStock(increasedStock);
	}
}
