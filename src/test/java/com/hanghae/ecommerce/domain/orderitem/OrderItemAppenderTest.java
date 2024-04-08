package com.hanghae.ecommerce.domain.orderitem;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.product.Product;
import com.hanghae.ecommerce.storage.order.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class OrderItemAppenderTest {
    private OrderItemAppender orderItemAppender;

    private OrderItemRepository orderItemRepository;

    @BeforeEach
    void setUp() {
        orderItemRepository = mock(OrderItemRepository.class);

        orderItemAppender = new OrderItemAppender(orderItemRepository);
    }

    @Test
    @DisplayName("주문 아이템 생성")
    void create_order_items() {
        // Given
        Order order = Fixtures.order(OrderStatus.READY);
        List<Product> products = List.of(
                Fixtures.product("후드티"),
                Fixtures.product("맨투맨")
        );

        List<OrderRequest.ProductOrderRequest> productsOrderRequest = List.of(
                new OrderRequest.ProductOrderRequest(1L, 3L),
                new OrderRequest.ProductOrderRequest(2L, 2L)
        );


        given(orderItemRepository.createOrderItem(any())).willReturn(List.of(
                Fixtures.orderItem(1L, products.get(0).id()),
                Fixtures.orderItem(2L, products.get(1).id())
        ));

        // When
        List<OrderItem> orderItems = orderItemAppender.create(order, products, productsOrderRequest);

        // Then
        assertThat(orderItems.size()).isEqualTo(2);
        assertThat(orderItems.get(0).unitPrice()).isEqualTo(50_000L);
        assertThat(orderItems.get(0).totalPrice()).isEqualTo(150_000L);
    }
}
