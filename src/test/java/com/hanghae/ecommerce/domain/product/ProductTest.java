package com.hanghae.ecommerce.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    @DisplayName("상품 재고가 충분한 경우 에러가 발생하지 않는다")
    void when_enough_stock_quantity_then_return_true() {
        // Given
        Product product = new Product(1L, "슬랙스", 30_000L, "편한 슬랙스", 5L);
        Long orderQuantity = 3L;

        // When && Then
        assertDoesNotThrow(() -> {
            product.isEnoughProductStockQuantityForOrder(orderQuantity);
        });
    }

    @Test
    @DisplayName("상품 재고가 부족한 경우 에러가 발생한다.")
    void when_not_enough_stock_quantity_then_return_false() {
        // Given
        Product product = new Product(1L, "슬랙스", 30_000L, "편한 슬랙스", 1L);
        Long orderQuantity = 100L;

        // When

        // Then
        assertThrows(IllegalArgumentException.class, () -> {
            product.isEnoughProductStockQuantityForOrder(orderQuantity);
        });
    }

    @Test
    @DisplayName("상품 재고를 감소한다")
    void decrease_stock() {
        // Given
        Product product = new Product(1L, "슬랙스", 30_000L, "편한 슬랙스", 3L);
        Long orderStockQuantity = 2L;

        // When
        Product decreasedProduct = product.decreaseStock(orderStockQuantity);

        // Then
        assertThat(decreasedProduct.stockQuantity()).isEqualTo(3L - 2L);
    }
}
