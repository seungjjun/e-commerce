package com.hanghae.ecommerce.domain.orderitem;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class OrderItemReader {
	private final OrderItemRepository orderItemRepository;

	public OrderItemReader(OrderItemRepository orderItemRepository) {
		this.orderItemRepository = orderItemRepository;
	}

	public List<OrderItem> readAllByOrderId(Long orderId) {
		return orderItemRepository.findAllByOrderId(orderId);
	}
}
