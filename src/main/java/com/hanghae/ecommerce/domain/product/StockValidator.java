package com.hanghae.ecommerce.domain.product;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hanghae.ecommerce.api.dto.request.OrderRequest;

import jakarta.persistence.EntityNotFoundException;

@Component
public class StockValidator {
	public void checkProductStockQuantityForOrder(List<Stock> stocks, List<OrderRequest.ProductOrderRequest> products) {
		for (OrderRequest.ProductOrderRequest orderRequest : products) {
			Stock stock = stocks.stream()
				.filter(s -> s.productId().equals(orderRequest.id()))
				.findFirst()
				.orElseThrow(() -> new EntityNotFoundException(orderRequest.id() + " 상품의 정보를 찾지 못했습니다."));

			stock.isEnoughProductStockQuantityForOrder(orderRequest.quantity());
		}
	}
}
