package com.hanghae.ecommerce.domain.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.hanghae.ecommerce.api.error.InsufficientStockException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {
	@Test
	@DisplayName("상품 주문 총 금액을 계산한다.")
	void calculate_total_order_price() {
		// Given
		Product product = new Product(1L, "슬랙스", 30_000L, "편한 슬랙스", 5L);
		Long orderQuantity = 3L;

		// When
		Long orderTotalPrice = product.orderTotalPrice(orderQuantity);

		// Then
		assertThat(orderTotalPrice).isEqualTo(30_000L * 3);
	}

	@Test
	@DisplayName("상품의 재고를 업데이트 한다.")
	void update_product_stock() {
		// Given
		Product product = new Product(1L, "슬랙스", 30_000L, "편한 슬랙스", 5L);

		Long stockQuantity = 3L;

		// When
		Product updatedProduct = product.updateStock(stockQuantity);

		// Then
		assertThat(updatedProduct.stockQuantity()).isEqualTo(3L);
	}

	@Test
	@DisplayName("상품의 재고를 감소시킨다.")
	void decrease_product_stock_quantity() {
		// Given
		Product product = new Product(1L, "슬랙스", 30_000L, "편한 슬랙스", 5L);

		Long stockQuantity = 3L;

		// When
		Product decreasedStock = product.decreaseStock(stockQuantity);

		// Then
		assertThat(decreasedStock.stockQuantity()).isEqualTo(5L - 3L);
	}

	@Test
	@DisplayName("상품의 재고를 확인하고 부족하면 예외가 발생한다.")
	void check_stock_quantity() {
		// Given
		Product product = new Product(1L, "슬랙스", 30_000L, "편한 슬랙스", 5L);

		Long quantity = 10L;

		// When && Then
		assertThrows(InsufficientStockException.class, () -> {
			product.isEnoughStockQuantity(quantity);
		});
	}
}
