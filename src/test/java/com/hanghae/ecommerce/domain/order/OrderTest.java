package com.hanghae.ecommerce.domain.order;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.storage.order.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class OrderTest {

    @Test
    @DisplayName("주문 상태 변경 테스트")
    void changeOrderStatus() {
        // Given
        Order order = Fixtures.order(OrderStatus.READY);

        // When
        Order changedOrderStatus = order.changeStatus(OrderStatus.PAID);

        // Then
        assertThat(changedOrderStatus.orderStatus()).isEqualTo(OrderStatus.PAID.toString());
    }
}
