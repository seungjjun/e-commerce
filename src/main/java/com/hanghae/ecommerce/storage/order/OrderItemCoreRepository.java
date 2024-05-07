package com.hanghae.ecommerce.storage.order;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanghae.ecommerce.domain.order.OrderItem;
import com.hanghae.ecommerce.domain.order.OrderItemRepository;

import jakarta.persistence.EntityNotFoundException;

@Repository
public class OrderItemCoreRepository implements OrderItemRepository {
	private final OrderItemJpaRepository orderItemJpaRepository;

	public OrderItemCoreRepository(OrderItemJpaRepository orderItemJpaRepository) {
		this.orderItemJpaRepository = orderItemJpaRepository;
	}

	@Override
	public List<OrderItem> findAllByOrderId(Long orderId) {
		return orderItemJpaRepository.findAllByOrderId(orderId).stream()
			.map(OrderItemEntity::toOrderItem)
			.toList();
	}

	@Override
	public void updateStatus(OrderItem item, OrderItemStatus orderItemStatus) {
		OrderItemEntity orderItemEntity = orderItemJpaRepository.findById(item.id())
			.orElseThrow(() -> new EntityNotFoundException("주문 아이템이 존재하지 않습니다. id - " + item.id()));

		orderItemEntity.updateStatus(orderItemStatus);
	}
}
