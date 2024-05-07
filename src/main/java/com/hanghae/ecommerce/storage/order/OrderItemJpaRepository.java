package com.hanghae.ecommerce.storage.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemJpaRepository extends JpaRepository<OrderItemEntity, Long> {
	List<OrderItemEntity> findAllByOrderId(Long orderId);
}
