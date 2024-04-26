package com.hanghae.ecommerce.storage.payment;

import com.hanghae.ecommerce.domain.payment.Payment;
import com.hanghae.ecommerce.storage.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentEntity extends BaseEntity {
	@Column(name = "order_id")
	private Long orderId;

	@Column(name = "pay_amount")
	private Long payAmount;

	@Column(name = "payment_method")
	@Enumerated(EnumType.STRING)
	private PayType paymentMethod;

	public PaymentEntity(Long orderId, Long payAmount, PayType paymentMethod) {
		this.orderId = orderId;
		this.payAmount = payAmount;
		this.paymentMethod = paymentMethod;
	}

	public Payment toPayment() {
		return new Payment(getId(), orderId, payAmount, paymentMethod.toString(), getCreatedAt());
	}
}
