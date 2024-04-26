package com.hanghae.ecommerce.storage.order;

import java.time.LocalDateTime;

import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.storage.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderEntity extends BaseEntity {
	private Long userId;

	private Long payAmount;

	private String receiverName;

	private String address;

	private String phoneNumber;

	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;

	private LocalDateTime orderedAt;

	public OrderEntity(Long userId,
						Long payAmount,
						String receiverName,
						String address,
						String phoneNumber,
						OrderStatus orderStatus,
						LocalDateTime orderedAt) {
		this.userId = userId;
		this.payAmount = payAmount;
		this.receiverName = receiverName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.orderStatus = orderStatus;
		this.orderedAt = orderedAt;
	}

	public Order toOrder() {
		return new Order(getId(), userId, payAmount, receiverName, address, phoneNumber, orderStatus.toString(),
			orderedAt);
	}

	public void updateStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
}
