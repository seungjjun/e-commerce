package com.hanghae.ecommerce.domain.product;

import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StockUpdator {
	private final StockRepository stockRepository;

	public StockUpdator(StockRepository stockRepository) {
		this.stockRepository = stockRepository;
	}

	public List<Stock> decreaseStock(List<Stock> stocks, List<OrderRequest.ProductOrderRequest> products) {
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
		return stockList;
	}

	public void compensateStock(List<Stock> stocks, List<OrderRequest.ProductOrderRequest> products) {
		for (OrderRequest.ProductOrderRequest orderRequest : products) {
			Stock stock = stocks.stream().filter(s -> s.productId().equals(orderRequest.id()))
					.findFirst()
					.get();

			Stock increasedStock = stock.increaseStock(orderRequest.quantity());
			stockRepository.updateStock(increasedStock);
		}
	}
}
