package com.hanghae.ecommerce.domain.orderitem;

import com.hanghae.ecommerce.storage.orderitem.OrderItemEntity;

import java.util.List;

public interface OrderItemRepository {
    List<OrderItem> createOrderItem(List<OrderItemEntity> orderItemEntities);
}
