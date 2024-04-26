package com.hanghae.ecommerce.storage.orderitem;

import com.hanghae.ecommerce.domain.orderitem.OrderItem;
import com.hanghae.ecommerce.storage.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItemEntity extends BaseEntity {
	@Column(name = "order_id")
	private Long orderId;

	@Column(name = "product_id")
	private Long productId;

	@Column(name = "product_name")
	private String productName;

	@Column(name = "unit_price")
	private Long unitPrice;

	@Column(name = "total_price")
	private Long totalPrice;

	@Column(name = "quantity")
	private Long quantity;

	public OrderItemEntity(
		Long orderId,
		Long productId,
		String productName,
		Long unitPrice,
		Long totalPrice,
		Long quantity) {
		this.orderId = orderId;
		this.productId = productId;
		this.productName = productName;
		this.unitPrice = unitPrice;
		this.totalPrice = totalPrice;
		this.quantity = quantity;
	}

	public OrderItem toOrderItem() {
		return new OrderItem(
			getId(),
			orderId,
			productId,
			productName,
			unitPrice,
			totalPrice,
			quantity
		);
	}
}
