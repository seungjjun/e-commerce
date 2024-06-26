package com.hanghae.ecommerce.storage.order;

import java.time.LocalDateTime;
import java.util.List;

import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.order.OrderItem;
import com.hanghae.ecommerce.storage.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders", indexes = @Index(name = "idx_order", columnList = "ordered_at, order_status"))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderEntity extends BaseEntity {
	private Long userId;

	private Long payAmount;

	private String receiverName;

	private String address;

	private String phoneNumber;

	private String paymentMethod;

	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;

	private LocalDateTime orderedAt;

	public OrderEntity(Long userId,
					   Long payAmount,
					   String receiverName,
					   String address,
					   String phoneNumber,
					   String paymentMethod,
					   OrderStatus orderStatus,
					   LocalDateTime orderedAt) {
		this.userId = userId;
		this.payAmount = payAmount;
		this.receiverName = receiverName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.paymentMethod = paymentMethod;
		this.orderStatus = orderStatus;
		this.orderedAt = orderedAt;
	}

	public Order toOrder(List<OrderItem> items) {
		return new Order(
			getId(), userId,
			payAmount, items,
			receiverName, address,
			phoneNumber, paymentMethod,
			orderStatus.toString(), orderedAt);
	}

	public void updateStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
}
