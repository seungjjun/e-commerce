package com.hanghae.ecommerce.domain.product;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<Stock> decreaseProductStock(List<Product> products, OrderRequest request) {
		List<Stock> stocks = stockReader.readByProductIds(products);
		stockValidator.checkProductStockQuantityForOrder(stocks, request.products());
		return stockUpdator.updateStockForOrder(stocks, request.products());
	}
}
