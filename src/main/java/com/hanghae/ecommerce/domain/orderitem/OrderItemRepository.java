package com.hanghae.ecommerce.domain.orderitem;

import java.util.List;

import com.hanghae.ecommerce.storage.orderitem.OrderItemEntity;

public interface OrderItemRepository {
	List<OrderItem> createOrderItem(List<OrderItemEntity> orderItemEntities);

	List<OrderItem> findAllByOrderId(Long orderId);
}
