package com.hanghae.ecommerce.storage.orderitem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemJpaRepository extends JpaRepository<OrderItemEntity, Long> {
	List<OrderItemEntity> findAllByOrderId(Long orderId);
}
