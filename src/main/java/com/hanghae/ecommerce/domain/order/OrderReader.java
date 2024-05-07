package com.hanghae.ecommerce.domain.order;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderReader {

	private final OrderRepository orderRepository;

	public Order read(Order order) {
		return orderRepository.findById(order.id());
	}
}
