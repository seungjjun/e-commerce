package com.hanghae.ecommerce.domain.product;

import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
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

	public List<Stock> decreaseProductStock(List<Stock> stocks, List<OrderRequest.ProductOrderRequest> request) {
		stockValidator.checkProductStockQuantityForOrder(stocks, request);
		return stockUpdator.decreaseStock(stocks, request);
	}

	public void compensateStock(List<Stock> decreasedStocks, OrderRequest request) {
		stockUpdator.compensateStock(decreasedStocks, request.products());
	}
}
