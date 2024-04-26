package com.hanghae.ecommerce.domain.product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hanghae.ecommerce.api.dto.request.OrderRequest;

import jakarta.persistence.EntityNotFoundException;

@Component
public class StockUpdator {
	private final StockRepository stockRepository;

	public StockUpdator(StockRepository stockRepository) {
		this.stockRepository = stockRepository;
	}

	public List<Stock> updateStockForOrder(List<Stock> stocks, List<OrderRequest.ProductOrderRequest> products) {
		List<Stock> stockList = new ArrayList<>();
		for (OrderRequest.ProductOrderRequest orderRequest : products) {
			Stock stock = stocks.stream()
				.filter(s -> s.productId().equals(orderRequest.id()))
				.findFirst()
				.orElseThrow(() -> new EntityNotFoundException(orderRequest.id() + " 상품의 정보를 찾지 못했습니다."));

			Stock decreasedStock = stock.decreaseStock(orderRequest.quantity());
			stockRepository.updateStock(decreasedStock);
			stockList.add(decreasedStock);
		}
		return stocks;
	}
}
