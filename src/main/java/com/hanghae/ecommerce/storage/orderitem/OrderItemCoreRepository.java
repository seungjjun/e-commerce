package com.hanghae.ecommerce.storage.orderitem;

import com.hanghae.ecommerce.domain.orderitem.OrderItem;
import com.hanghae.ecommerce.domain.orderitem.OrderItemRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderItemCoreRepository implements OrderItemRepository {
    private final OrderItemJpaRepository orderItemJpaRepository;

    public OrderItemCoreRepository(OrderItemJpaRepository orderItemJpaRepository) {
        this.orderItemJpaRepository = orderItemJpaRepository;
    }

    @Override
    public List<OrderItem> createOrderItem(List<OrderItemEntity> orderItemEntities) {
        return orderItemJpaRepository.saveAll(orderItemEntities)
                .stream().map(OrderItemEntity::toOrderItem)
                .toList();
    }

    @Override
    public List<OrderItem> findAllByOrderId(Long orderId) {
        return orderItemJpaRepository.findAllByOrderId(orderId).stream()
                .map(OrderItemEntity::toOrderItem)
                .toList();
    }
}
