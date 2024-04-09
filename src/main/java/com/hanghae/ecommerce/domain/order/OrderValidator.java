package com.hanghae.ecommerce.domain.order;

import com.hanghae.ecommerce.storage.order.OrderStatus;
import org.springframework.stereotype.Component;

@Component
public class OrderValidator {
    public void checkOrderStatusForPay(Order order) {
        if (order.orderStatus().equals(OrderStatus.CANCELED.toString())) {
            throw new IllegalArgumentException("주문이 취소된 상태 입니다.");
        }

        if (order.orderStatus().equals(OrderStatus.PAID.toString())) {
            throw new IllegalArgumentException("이미 결제가 완료된 주문 입니다.");
        }
    }
}
