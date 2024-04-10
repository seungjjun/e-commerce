package com.hanghae.ecommerce.domain.user;

import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.order.OrderUpdater;
import com.hanghae.ecommerce.domain.product.ProductManager;
import com.hanghae.ecommerce.storage.order.OrderStatus;
import org.springframework.stereotype.Component;

@Component
public class UserPointValidator {
    private final OrderUpdater orderUpdater;
    private final ProductManager productManager;

    public UserPointValidator(OrderUpdater orderUpdater, ProductManager productManager) {
        this.orderUpdater = orderUpdater;
        this.productManager = productManager;
    }

    public void checkUserPointForPay(Order order, User user, Long payAmount) {
        if (user.point() < payAmount) {
            orderUpdater.changeStatus(order, OrderStatus.PAY_FAILED);
            productManager.compensateProduct(order);
            throw new IllegalArgumentException("현재 잔액이 " + user.point() + " 으로 잔액이 충분하지 않습니다.");
        }
    }
}
