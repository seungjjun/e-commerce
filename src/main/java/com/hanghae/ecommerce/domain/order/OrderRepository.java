package com.hanghae.ecommerce.domain.order;

import com.hanghae.ecommerce.storage.order.OrderStatus;

public interface OrderRepository {
	void updateStatus(Order order, OrderStatus orderStatus);

	Order create(Long userId, OrderForm orderForm);

	Order findById(Long orderId);
}
