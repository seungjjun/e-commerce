package com.hanghae.ecommerce.domain.product;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.order.OrderUpdater;
import com.hanghae.ecommerce.storage.order.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ProductValidatorTest {
    private ProductValidator productValidator;

    private OrderUpdater orderUpdater;

    @BeforeEach
    void setUp() {
        orderUpdater = mock(OrderUpdater.class);

        productValidator = new ProductValidator(orderUpdater);
    }

    @Test
    @DisplayName("상품 재고가 부족하지 않은 경우 order 상태를 업데이트 하지 않는다.")
    void when_enough_product_stock_then_not_call_change_order_status() {
        // Given
        Order order = Fixtures.order(OrderStatus.READY);
        List<Product> products = List.of(
                Fixtures.product("후드티"),
                Fixtures.product("맨투맨")
        );

        List<OrderRequest.ProductOrderRequest> productsOrderRequest = List.of(
                new OrderRequest.ProductOrderRequest(1L, 5L),
                new OrderRequest.ProductOrderRequest(2L, 10L)
        );

        // When
        productValidator.checkProductStockQuantityForOrder(order, products, productsOrderRequest);

        // Then
        verify(orderUpdater, never()).changeStatus(any(), any());
    }

    @Test
    @DisplayName("상품 재고가 부족한 경우 order 상태를 업데이트 한다.")
    void when_not_enough_product_stock_then_call_change_order_status() {
        // Given
        Order order = Fixtures.order(OrderStatus.READY);
        List<Product> products = List.of(
                Fixtures.product("후드티"),
                Fixtures.product("맨투맨")
        );

        List<OrderRequest.ProductOrderRequest> productsOrderRequest = List.of(
                new OrderRequest.ProductOrderRequest(1L, 100L),
                new OrderRequest.ProductOrderRequest(2L, 10L)
        );

        // When
        assertThrows(IllegalArgumentException.class, () -> {
            productValidator.checkProductStockQuantityForOrder(order, products, productsOrderRequest);
        });

        // Then
        verify(orderUpdater, atLeastOnce()).changeStatus(any(), any());

    }

}
