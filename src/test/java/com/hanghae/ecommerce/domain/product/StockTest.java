package com.hanghae.ecommerce.domain.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StockTest {

	@Test
	@DisplayName("상품 재고가 충분한 경우 에러가 발생하지 않는다")
	void when_enough_stock_quantity_then_return_true() {
		// Given
		Stock stock = new Stock(1L, 1L, 5L);
		Long orderQuantity = 3L;

		// When && Then
		assertDoesNotThrow(() -> {
			stock.isEnoughProductStockQuantityForOrder(orderQuantity);
		});
	}

	@Test
	@DisplayName("상품 재고가 부족한 경우 에러가 발생한다.")
	void when_not_enough_stock_quantity_then_return_false() {
		// Given
		Stock stock = new Stock(1L, 1L, 1L);
		Long orderQuantity = 100L;

		// When

		// Then
		assertThrows(IllegalArgumentException.class, () -> {
			stock.isEnoughProductStockQuantityForOrder(orderQuantity);
		});
	}

	@Test
	@DisplayName("상품 재고를 감소한다")
	void decrease_stock() {
		// Given
		Stock stock = new Stock(1L, 1L, 3L);
		Long orderStockQuantity = 2L;

		// When
		Stock decreasedProductStock = stock.decreaseQuantity(orderStockQuantity);

		// Then
		assertThat(decreasedProductStock.stockQuantity()).isEqualTo(3L - 2L);
	}

	@Test
	@DisplayName("상품 재고를 증가시킨다")
	void increase_stock() {
		// Given
		Stock stock = new Stock(1L, 1L, 1L);
		Long stockQuantity = 2L;

		// When
		Stock increasedQuantity = stock.increaseQuantity(stockQuantity);

		// Then
		assertThat(increasedQuantity.stockQuantity()).isEqualTo(1L + 2L);
	}
}
