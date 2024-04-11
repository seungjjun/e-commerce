package com.hanghae.ecommerce.domain.order;

import com.hanghae.ecommerce.storage.order.OrderStatus;
import org.springframework.stereotype.Component;

@Component
public class OrderValidator {
    public boolean isOrderStatusWaitingForPay(Order order) {
        return order.orderStatus().equals(OrderStatus.WAITING_FOR_PAY.toString());
    }
}
