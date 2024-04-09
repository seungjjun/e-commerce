package com.hanghae.ecommerce.domain.user;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.order.OrderUpdater;
import com.hanghae.ecommerce.storage.order.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserPointValidatorTest {
    private UserPointValidator userPointValidator;
    private OrderUpdater orderUpdater;

    @BeforeEach
    void setUp() {
        orderUpdater = mock(OrderUpdater.class);

        userPointValidator = new UserPointValidator(orderUpdater);
    }

    @Test
    @DisplayName("결제 금액보다 포인트가 적은 경우 에러가 발생한다.")
    void when_not_enough_point_then_failed_use_point() {
        // Given
        User user = Fixtures.user(1L);
        Long payAmount = 999_999L;
        Order order = Fixtures.order(OrderStatus.COMPLETE);

        // When && Then
        assertThrows(IllegalArgumentException.class, () -> {
            userPointValidator.checkUserPointForPay(order, user, payAmount);
        });

        verify(orderUpdater, atLeastOnce()).changeStatus(any(), any());
    }

    @Test
    @DisplayName("결제 금액보다 포인트가 많은 경우 에러가 발생하지 않는다.")
    void when_enough_point_then_succeed_use_point() {
        // Given
        User user = Fixtures.user(1L);
        Long payAmount = 1_000L;
        Order order = Fixtures.order(OrderStatus.COMPLETE);

        // When
        userPointValidator.checkUserPointForPay(order, user, payAmount);

        // Then
        assertDoesNotThrow(() -> new IllegalArgumentException());
        verify(orderUpdater, never()).changeStatus(any(), any());
    }
}
