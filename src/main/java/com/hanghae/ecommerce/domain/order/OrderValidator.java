package com.hanghae.ecommerce.domain.order;

import org.springframework.stereotype.Component;

import com.hanghae.ecommerce.storage.order.OrderStatus;

@Component
public class OrderValidator {
	public boolean isOrderStatusWaitingForPay(Order order) {
		return order.orderStatus().equals(OrderStatus.WAITING_FOR_PAY.toString());
	}
}
