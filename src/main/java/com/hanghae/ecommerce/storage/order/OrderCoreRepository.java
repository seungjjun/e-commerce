package com.hanghae.ecommerce.storage.order;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.order.OrderForm;
import com.hanghae.ecommerce.domain.order.OrderItem;
import com.hanghae.ecommerce.domain.order.OrderRepository;
import com.hanghae.ecommerce.domain.user.User;

import jakarta.persistence.EntityNotFoundException;

@Repository
public class OrderCoreRepository implements OrderRepository {
	private final OrderJpaRepository orderJpaRepository;
	private final OrderItemJpaRepository orderItemJpaRepository;

	public OrderCoreRepository(OrderJpaRepository orderJpaRepository, OrderItemJpaRepository orderItemJpaRepository) {
		this.orderJpaRepository = orderJpaRepository;
		this.orderItemJpaRepository = orderItemJpaRepository;
	}

	@Override
	public Order findById(Long orderId) {
		List<OrderItem> orderItems = orderItemJpaRepository.findAllByOrderId(orderId).stream()
			.map(OrderItemEntity::toOrderItem)
			.toList();

		return orderJpaRepository.findById(orderId)
			.orElseThrow(() -> new EntityNotFoundException("주문 정보를 찾지 못했습니다. - id: " + orderId))
			.toOrder(orderItems);
	}

	@Override
	public Order create(User user, OrderForm orderForm) {
		OrderEntity order = orderJpaRepository.save(new OrderEntity(
			user.id(),
			orderForm.payAmount(),
			orderForm.receiverName(),
			orderForm.address(),
			orderForm.phoneNumber(),
			OrderStatus.READY,
			LocalDateTime.now()
		));

		List<OrderItem> items = orderItemJpaRepository.saveAll(
				orderForm.orderProducts().stream()
					.map(p -> new OrderItemEntity(
						order.getId(),
						p.productId(),
						p.productName(),
						p.unitPrice(),
						p.totalPrice(),
						p.quantity(),
						OrderItemStatus.CREATED
					)).toList())
			.stream().map(OrderItemEntity::toOrderItem)
			.toList();

		return order.toOrder(items);
	}

	@Override
	public void updateStatus(Order order, OrderStatus orderStatus) {
		OrderEntity orderEntity = orderJpaRepository.findById(order.id())
			.orElseThrow(() -> new EntityNotFoundException("주문 정보를 찾지 못했습니다. - id: " + order.id()));
		orderEntity.updateStatus(orderStatus);
		orderJpaRepository.save(orderEntity);
	}

}
