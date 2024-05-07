package com.hanghae.ecommerce.domain.payment;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.order.OrderUpdater;
import com.hanghae.ecommerce.domain.user.User;
import com.hanghae.ecommerce.domain.user.UserPointManager;
import com.hanghae.ecommerce.storage.order.OrderStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

	private final OrderUpdater orderUpdater;
	private final PaymentAppender paymentAppender;
	private final UserPointManager userPointManager;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Payment pay(User user, Order order, OrderRequest request) {
		userPointManager.usePoint(user, request.paymentAmount());
		orderUpdater.changeStatus(order, OrderStatus.PAID);
		return paymentAppender.create(order, request.paymentAmount(), request.paymentMethod());
	}
}
