package com.hanghae.ecommerce.domain.user;

import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.order.OrderUpdater;
import com.hanghae.ecommerce.storage.order.OrderStatus;
import org.springframework.stereotype.Component;

@Component
public class UserPointValidator {
    private final OrderUpdater orderUpdater;

    public UserPointValidator(OrderUpdater orderUpdater) {
        this.orderUpdater = orderUpdater;
    }

    public void checkUserPointForPay(Order order, User user, Long payAmount) {
        if (user.point() < payAmount) {
            orderUpdater.changeStatus(order, OrderStatus.PAY_FAILED);
            throw new IllegalArgumentException("현재 잔액이 " + user.point() + " 으로 잔액이 충분하지 않습니다.");
        }
    }
}
