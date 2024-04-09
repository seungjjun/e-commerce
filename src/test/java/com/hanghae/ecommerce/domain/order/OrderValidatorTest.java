package com.hanghae.ecommerce.domain.order;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.storage.order.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderValidatorTest {
    private OrderValidator orderValidator;

    @BeforeEach
    void setUp() {
        orderValidator = new OrderValidator();
    }

    @Test
    @DisplayName("주문 상태가 취소인 경우 에러가 발생한다.")
    void when_order_status_is_canceled_then_fail_pay() {
        // Given
        Order order = Fixtures.order(OrderStatus.CANCELED);

        // When && Then
        assertThrows(IllegalArgumentException.class, () -> {
            orderValidator.checkOrderStatusForPay(order);
        });
    }

    @Test
    @DisplayName("주문 상태가 이미 결제가 완료된 경우 에러가 발생한다.")
    void when_order_status_is_paid_then_fail_pay() {
        // Given
        Order order = Fixtures.order(OrderStatus.PAID);

        // When && Then
        assertThrows(IllegalArgumentException.class, () -> {
            orderValidator.checkOrderStatusForPay(order);
        });
    }

}
