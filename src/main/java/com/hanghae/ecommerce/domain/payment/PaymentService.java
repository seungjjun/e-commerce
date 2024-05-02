package com.hanghae.ecommerce.domain.payment;

import org.springframework.stereotype.Service;

import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.common.LockHandler;
import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.order.OrderUpdater;
import com.hanghae.ecommerce.domain.order.OrderValidator;
import com.hanghae.ecommerce.domain.user.User;
import com.hanghae.ecommerce.domain.user.UserPointManager;
import com.hanghae.ecommerce.storage.order.OrderStatus;

@Service
public class PaymentService {

	private final OrderUpdater orderUpdater;
	private final OrderValidator orderValidator;
	private final PaymentAppender paymentAppender;
	private final UserPointManager userPointManager;
	private final LockHandler lockHandler;

	private static final String USER_POINT_LOCK_PREFIX = "USER_";

	public PaymentService(
		OrderUpdater orderUpdater,
		OrderValidator orderValidator,
		PaymentAppender paymentAppender,
		UserPointManager userPointManager,
		LockHandler lockHandler) {
		this.orderUpdater = orderUpdater;
		this.orderValidator = orderValidator;
		this.paymentAppender = paymentAppender;
		this.userPointManager = userPointManager;
		this.lockHandler = lockHandler;
	}

	public Payment pay(User user, Order order, OrderRequest request) {
		if (!orderValidator.isOrderStatusWaitingForPay(order)) {
			throw new IllegalArgumentException("결제 대기 상태가 아닙니다. order status : " + order.orderStatus());
		}

		String key = USER_POINT_LOCK_PREFIX + user.id();
		lockHandler.lock(key, 2, 1);
		try {
			userPointManager.usePoint(user, request.paymentAmount());
		} finally {
			lockHandler.unlock(key);
		}

		Order paidOrder = orderUpdater.changeStatus(order, OrderStatus.PAID);
		return paymentAppender.create(paidOrder, request.paymentAmount(), request.paymentMethod());
	}
}
