package com.hanghae.ecommerce.domain.order;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.storage.order.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class OrderUpdaterTest {
    private OrderUpdater orderUpdater;

    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);

        orderUpdater = new OrderUpdater(orderRepository);
    }

    @Test
    @DisplayName("주문 상태를 변경한다.")
    void change_order_status() {
        // Given
        Order readyOrder = Fixtures.order(OrderStatus.READY);
        Order canceledOrder = Fixtures.order(OrderStatus.CANCELED);

        given(orderRepository.updateStatus(any(), any())).willReturn(canceledOrder);

        // When
        Order order = orderUpdater.changeStatus(readyOrder, OrderStatus.CANCELED);

        // Then
        assertThat(order.orderStatus()).isEqualTo("CANCELED");
    }

}
