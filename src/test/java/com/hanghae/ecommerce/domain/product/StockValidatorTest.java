package com.hanghae.ecommerce.domain.product;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.hanghae.ecommerce.api.dto.request.OrderRequest;

import jakarta.persistence.EntityNotFoundException;

class StockValidatorTest {
	private StockValidator stockValidator;
	private Stock stock;

	@BeforeEach
	void setUp() {
		stock = mock(Stock.class);

		stockValidator = new StockValidator();
	}

	@Test
	@DisplayName("주문 결제 시 상품의 재고를 검증하는 메서드가 호출되는지 테스")
	void when_product_stock_quantity_for_order_then_call_is_enough_product_stock_quantity_for_order() {
		// Given
		Long quantity = 5L;
		OrderRequest.ProductOrderRequest productOrderRequest = new OrderRequest.ProductOrderRequest(1L, quantity);
		List<OrderRequest.ProductOrderRequest> productsOrderRequest = List.of(productOrderRequest);

		given(stock.productId()).willReturn(1L);

		// When
		stockValidator.checkProductStockQuantityForOrder(List.of(stock), productsOrderRequest);

		// Then
		verify(stock, times(1)).isEnoughProductStockQuantityForOrder(quantity);
	}

	@Test
	@DisplayName("주문 결제 시 주문 요청 상품의 id와 상품 id가 일치하지 않는 경우 예외 처리 테스트")
	void when_product_stock_quantity_for_order_then_throw_entity_not_found_exception() {
		// Given
		Long quantity = 5L;
		OrderRequest.ProductOrderRequest productOrderRequest = new OrderRequest.ProductOrderRequest(1L, quantity);
		List<OrderRequest.ProductOrderRequest> productsOrderRequest = List.of(productOrderRequest);

		given(stock.productId()).willReturn(2L);

		// When & Then
		assertThrows(EntityNotFoundException.class, () -> {
			stockValidator.checkProductStockQuantityForOrder(Collections.singletonList(stock), productsOrderRequest);
		});
	}

}
