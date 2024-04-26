package com.hanghae.ecommerce.domain.product;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hanghae.ecommerce.api.dto.request.OrderRequest;

@Service
public class StockService {
	private final StockReader stockReader;
	private final StockValidator stockValidator;
	private final StockUpdator stockUpdator;

	public StockService(StockReader stockReader, StockValidator stockValidator, StockUpdator stockUpdator) {
		this.stockReader = stockReader;
		this.stockValidator = stockValidator;
		this.stockUpdator = stockUpdator;
	}

	public List<Stock> getStocksByProductIds(List<Product> products) {
		return stockReader.readByProductIds(products);
	}

	public List<Stock> decreaseProductStock(List<Stock> stocks, OrderRequest request) {
		stockValidator.checkProductStockQuantityForOrder(stocks, request.products());
		return stockUpdator.updateStockForOrder(stocks, request.products());
	}
}
