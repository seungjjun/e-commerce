package com.hanghae.ecommerce.domain.product;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.domain.order.OrderItem;

class StockUpdatorTest {
	private StockUpdator stockUpdator;
	private StockRepository stockRepository;

	private Stock mockStock;
	private Product product;
	private OrderItem item;

	@BeforeEach
	void setUp() {
		stockRepository = mock(StockRepository.class);

		stockUpdator = new StockUpdator(stockRepository);

		mockStock = mock(Stock.class);
		product = Fixtures.product("후드티");
		item = new OrderItem(
			1L, 1L,
			product.id(), product.name(),
			product.price(), product.orderTotalPrice(3L),
			3L, "CREATED");
	}

	@Test
	@DisplayName("재고 감소 업데이트 메서드 호출 시 stock의 재고 감소 메서드가 호출 된다.")
	void decrease_product_stock() {
		// Given
		given(mockStock.productId()).willReturn(1L);
		given(mockStock.stockQuantity()).willReturn(5L);

		// When
		stockUpdator.decreaseStock(mockStock, item);

		// Then
		verify(stockRepository, atLeastOnce()).updateStock(any());
		verify(mockStock, atLeastOnce()).decreaseQuantity(any());
	}

	@Test
	@DisplayName("재고 증가 업데이트 메서드 호출 시 stock의 재고 증가 메서드가 호출 된다.")
	void increase_product_stock() {
		// Given
		given(mockStock.productId()).willReturn(1L);
		given(mockStock.stockQuantity()).willReturn(5L);

		// When
		stockUpdator.increaseStock(mockStock, item);

		// Then
		verify(stockRepository, atLeastOnce()).updateStock(any());
		verify(mockStock, atLeastOnce()).increaseQuantity(any());
	}
}
