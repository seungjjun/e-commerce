package com.hanghae.ecommerce.domain.order;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.domain.cart.Cart;
import com.hanghae.ecommerce.storage.order.OrderItemStatus;
import com.hanghae.ecommerce.storage.order.OrderStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderReader orderReader;
	private final OrderAppender orderAppender;
	private final OrderUpdater orderUpdater;

	@Transactional
	public Order order(Long userId, Cart cart, OrderRequest request) {
		return orderAppender.append(userId, cart, request);
	}

	@Transactional
	public void updateItemStatus(OrderItem item, OrderItemStatus status) {
		orderUpdater.changeItemStatus(item, status);
	}

	@Transactional
	public void orderFailed(Order order) {
		orderUpdater.changeStatus(order, OrderStatus.FAIL);
	}

	@Transactional
	public Order getOrder(Long orderId) {
		return orderReader.read(orderId);
	}
}
