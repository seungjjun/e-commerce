package com.hanghae.ecommerce.domain.order;

import java.util.List;

import com.hanghae.ecommerce.storage.order.OrderItemStatus;

public interface OrderItemRepository {
	List<OrderItem> findAllByOrderId(Long orderId);

	void updateStatus(OrderItem item, OrderItemStatus orderItemStatus);
}
