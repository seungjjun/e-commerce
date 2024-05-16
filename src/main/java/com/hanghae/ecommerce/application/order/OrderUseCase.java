package com.hanghae.ecommerce.application.order;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.domain.cart.Cart;
import com.hanghae.ecommerce.domain.cart.CartService;
import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.order.OrderService;
import com.hanghae.ecommerce.domain.order.event.OrderCreatedEvent;
import com.hanghae.ecommerce.domain.order.event.OrderEventPublisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderUseCase {
	private final CartService cartService;
	private final OrderService orderService;
	private final OrderEventPublisher orderEventPublisher;

	@Transactional
	public Order order(Long userId, OrderRequest request) {
		Cart cart = cartService.getCart(userId);
		Order order = orderService.order(userId, cart, request);

		orderEventPublisher.publishEvent(new OrderCreatedEvent(order));
		return order;
	}
}
