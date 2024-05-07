package com.hanghae.ecommerce.domain.order;

import org.springframework.stereotype.Component;

import com.hanghae.ecommerce.storage.order.OrderItemStatus;
import com.hanghae.ecommerce.storage.order.OrderStatus;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderUpdater {
	private final OrderRepository orderRepository;
	private final OrderItemRepository orderItemRepository;

	public void changeStatus(Order order, OrderStatus orderStatus) {
		orderRepository.updateStatus(order, orderStatus);
	}

	public void changeItemStatus(OrderItem item, OrderItemStatus orderItemStatus) {
		orderItemRepository.updateStatus(item, orderItemStatus);
	}
}
