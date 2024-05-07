package com.hanghae.ecommerce.domain.product;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.domain.order.OrderItem;

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
		Product product = Fixtures.product("후드티");
		OrderItem item = new OrderItem(
			1L, 1L,
			product.id(), product.name(),
			product.price(), product.orderTotalPrice(3L),
			3L, "CREATED");

		// When
		stockValidator.checkProductStockQuantityForOrder(stock, item);

		// Then
		verify(stock, atLeastOnce()).isEnoughProductStockQuantityForOrder(any());
	}
}
