package com.hanghae.ecommerce.domain.order;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.domain.orderitem.OrderItem;
import com.hanghae.ecommerce.domain.orderitem.OrderItemAppender;
import com.hanghae.ecommerce.domain.product.Product;
import com.hanghae.ecommerce.domain.user.User;
import com.hanghae.ecommerce.storage.order.OrderStatus;

@Service
public class OrderService {

	private final OrderItemAppender orderItemAppender;
	private final OrderProcessor orderProcessor;
	private final OrderUpdater orderUpdater;

	public OrderService(OrderItemAppender orderItemAppender, OrderProcessor orderProcessor, OrderUpdater orderUpdater) {
		this.orderItemAppender = orderItemAppender;
		this.orderProcessor = orderProcessor;
		this.orderUpdater = orderUpdater;
	}

	public Order order(User user, List<Product> products, OrderRequest request) {
		Order order = orderProcessor.order(user, request);

		List<OrderItem> orderItems = orderItemAppender.create(order, products, request.products());
		return orderUpdater.changeStatus(order, OrderStatus.WAITING_FOR_PAY);
	}
}
