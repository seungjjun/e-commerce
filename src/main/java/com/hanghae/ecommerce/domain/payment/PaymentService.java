package com.hanghae.ecommerce.domain.payment;

import com.hanghae.ecommerce.api.dto.request.PaymentRequest;
import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.order.OrderReader;
import com.hanghae.ecommerce.domain.order.OrderUpdater;
import com.hanghae.ecommerce.domain.order.OrderValidator;
import com.hanghae.ecommerce.domain.user.User;
import com.hanghae.ecommerce.domain.user.UserPointManager;
import com.hanghae.ecommerce.domain.user.UserPointValidator;
import com.hanghae.ecommerce.domain.user.UserReader;
import com.hanghae.ecommerce.storage.order.OrderStatus;
import org.springframework.stereotype.Service;

@Service
public class PaymentService implements PaymentCoreService {

    private final UserReader userReader;
    private final OrderReader orderReader;
    private final OrderUpdater orderUpdater;
    private final OrderValidator orderValidator;
    private final PaymentAppender paymentAppender;
    private final UserPointManager userPointManager;
    private final UserPointValidator userPointValidator;

    public PaymentService(UserReader userReader,
                          OrderReader orderReader,
                          OrderUpdater orderUpdater, OrderValidator orderValidator,
                          PaymentAppender paymentAppender,
                          UserPointManager userPointManager,
                          UserPointValidator userPointValidator) {
        this.userReader = userReader;
        this.orderReader = orderReader;
        this.orderUpdater = orderUpdater;
        this.orderValidator = orderValidator;
        this.paymentAppender = paymentAppender;
        this.userPointManager = userPointManager;
        this.userPointValidator = userPointValidator;
    }

    @Override
    public Payment pay(Long userId, PaymentRequest request) {
        User user = userReader.readById(userId);

        Order order = orderReader.readById(request.orderId());
        orderValidator.checkOrderStatusForPay(order);

        userPointValidator.checkUserPointForPay(order, user, request.payAmount());
        userPointManager.usePoint(user, request.payAmount());

        orderUpdater.changeStatus(order, OrderStatus.PAID);
        return paymentAppender.create(order, request.payAmount(), request.paymentMethod());
    }
}
