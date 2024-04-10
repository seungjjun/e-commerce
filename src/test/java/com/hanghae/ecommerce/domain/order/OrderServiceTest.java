package com.hanghae.ecommerce.domain.order;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.api.dto.request.Receiver;
import com.hanghae.ecommerce.domain.orderitem.OrderItemAppender;
import com.hanghae.ecommerce.domain.product.ProductReader;
import com.hanghae.ecommerce.domain.product.ProductUpdater;
import com.hanghae.ecommerce.domain.product.ProductValidator;
import com.hanghae.ecommerce.domain.user.User;
import com.hanghae.ecommerce.domain.user.UserReader;
import com.hanghae.ecommerce.storage.order.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class OrderServiceTest {
    private OrderService orderService;
    private UserReader userReader;
    private ProductReader productReader;
    private ProductUpdater productUpdater;
    private OrderItemAppender orderItemAppender;
    private OrderProcessor orderProcessor;
    private OrderUpdater orderUpdater;
    private ProductValidator productValidator;

    private User user;
    private OrderRequest request;

    @BeforeEach
    void setUp() {
        userReader = mock(UserReader.class);
        productReader = mock(ProductReader.class);
        productUpdater = mock(ProductUpdater.class);
        orderItemAppender = mock(OrderItemAppender.class);
        orderProcessor = mock(OrderProcessor.class);
        orderUpdater = mock(OrderUpdater.class);
        productValidator = mock(ProductValidator.class);

        orderService = new OrderService(userReader, productReader, productUpdater, orderItemAppender, orderProcessor, orderUpdater, productValidator);

        user = Fixtures.user(1L);
        request = new OrderRequest(
                new Receiver(
                        user.name(),
                        user.address(),
                        user.phoneNumber()
                ),
                List.of(
                        new OrderRequest.ProductOrderRequest(1L, 1L)
                ),
                50_000L
        );
    }

    @Test
    @DisplayName("주문 생성 성공 - 주문 상태 complete")
    void order_status_is_complete() {
        // Given
        Order readyOrder = Fixtures.order(OrderStatus.READY);
        Order completedOrder = Fixtures.order(OrderStatus.COMPLETE);

        given(userReader.readById(anyLong())).willReturn(user);
        given(orderProcessor.order(any(), any())).willReturn(readyOrder);
        given(productReader.readAllByIds(any())).willReturn(List.of());
        given(orderUpdater.changeStatus(any(), any())).willReturn(completedOrder);

        // When
        Order order = orderService.order(user.id(), request);

        // Then
        assertThat(order).isNotNull();
        assertThat(order.payAmount()).isEqualTo(89_000L);
        assertThat(order.orderStatus()).isEqualTo("complete");
        verify(productUpdater, atLeastOnce()).updateStockForOrder(any(), any());
        verify(orderUpdater, atLeastOnce()).changeStatus(any(), any());
    }
}
