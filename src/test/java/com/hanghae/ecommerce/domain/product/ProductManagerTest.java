package com.hanghae.ecommerce.domain.product;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.orderitem.OrderItem;
import com.hanghae.ecommerce.domain.orderitem.OrderItemReader;
import com.hanghae.ecommerce.storage.order.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class ProductManagerTest {
    private ProductManager productManager;

    private OrderItemReader orderItemReader;
    private ProductReader productReader;
    private ProductUpdater productUpdater;

    @BeforeEach
    void setUp() {
        orderItemReader = mock(OrderItemReader.class);
        productReader = mock(ProductReader.class);
        productUpdater = mock(ProductUpdater.class);

        productManager = new ProductManager(orderItemReader, productReader, productUpdater);
    }

    @Test
    @DisplayName("주문한 수량 만큼 다시 상품 재고를 증가시킨다.")
    void roll_back_product_stock() {
        // Given
        Order order = Fixtures.order(OrderStatus.PAY_FAILED);
        Product product = Fixtures.product("후드티");

        OrderItem orderItem = Fixtures.orderItem(order.id(), product.id());
        given(orderItemReader.readAllByOrderId(any())).willReturn(
                List.of(orderItem)
        );
        given(productReader.readById(any())).willReturn(product);

        // When
        productManager.compensateProduct(order);

        // Then
        verify(productUpdater, atLeastOnce()).updateStock(any());
    }

}
