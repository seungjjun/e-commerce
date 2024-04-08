package com.hanghae.ecommerce.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

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
    @DisplayName("상품 재고가 충분한 경우 true를 반환한다.")
    void when_enough_stock_quantity_then_return_true() {
        // Given
        Product product = new Product(1L, "슬랙스", 30_000L, "편한 슬랙스", 5L);
        Long orderQuantity = 3L;

        // When
        boolean result = product.isRemainingProductStock(orderQuantity);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("상품 재고가 부족한 경우 false를 반환한다.")
    void when_not_enough_stock_quantity_then_return_false() {
        // Given
        Product product = new Product(1L, "슬랙스", 30_000L, "편한 슬랙스", 1L);
        Long orderQuantity = 100L;

        // When
        boolean result = product.isRemainingProductStock(orderQuantity);

        // Then
        assertThat(result).isFalse();
    }
}
