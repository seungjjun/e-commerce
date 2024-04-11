package com.hanghae.ecommerce.domain.payment;

import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.order.OrderUpdater;
import com.hanghae.ecommerce.domain.order.OrderValidator;
import com.hanghae.ecommerce.domain.user.User;
import com.hanghae.ecommerce.domain.user.UserPointManager;
import com.hanghae.ecommerce.domain.user.UserPointValidator;
import com.hanghae.ecommerce.storage.order.OrderStatus;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final OrderUpdater orderUpdater;
    private final OrderValidator orderValidator;
    private final PaymentAppender paymentAppender;
    private final UserPointManager userPointManager;
    private final UserPointValidator userPointValidator;

    public PaymentService(OrderUpdater orderUpdater, OrderValidator orderValidator,
                          PaymentAppender paymentAppender,
                          UserPointManager userPointManager,
                          UserPointValidator userPointValidator) {
        this.orderUpdater = orderUpdater;
        this.orderValidator = orderValidator;
        this.paymentAppender = paymentAppender;
        this.userPointManager = userPointManager;
        this.userPointValidator = userPointValidator;
    }

    public Payment pay(User user, Order order, OrderRequest request) {
        if (!orderValidator.isOrderStatusWaitingForPay(order)) {
            throw new IllegalArgumentException("결제 대기 상태가 아닙니다. order status : " + order.orderStatus());
        }

        userPointValidator.checkUserPointForPay(user, request.paymentAmount());
        userPointManager.usePoint(user, request.paymentAmount());

        Order paidOrder = orderUpdater.changeStatus(order, OrderStatus.PAID);
        return paymentAppender.create(paidOrder, request.paymentAmount(), request.paymentMethod());
    }
}
