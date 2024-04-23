package com.hanghae.ecommerce.domain.order;

import com.hanghae.ecommerce.storage.order.OrderStatus;
import org.springframework.stereotype.Component;

@Component
public class OrderUpdater {
	private final OrderRepository orderRepository;

	public OrderUpdater(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	public Order changeStatus(Order order, OrderStatus orderStatus) {
		Order changedStatusOrder = order.changeStatus(orderStatus);
		return orderRepository.updateStatus(changedStatusOrder, orderStatus);
	}
}
