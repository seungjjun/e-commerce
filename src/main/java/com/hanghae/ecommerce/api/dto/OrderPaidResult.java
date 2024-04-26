package com.hanghae.ecommerce.api.dto;

import java.time.LocalDateTime;

import com.hanghae.ecommerce.api.dto.request.Receiver;
import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.payment.Payment;

public record OrderPaidResult(
	Long orderId,
	Long paymentId,
	Long payAmount,
	Receiver receiver,
	String paymentMethod,
	LocalDateTime orderedAt,
	LocalDateTime paidAt
) {
	public static OrderPaidResult of(Order order, Payment payment) {
		return new OrderPaidResult(
			order.id(),
			payment.id(),
			payment.payAmount(),
			new Receiver(
				order.receiverName(),
				order.address(),
				order.phoneNumber()
			),
			payment.paymentMethod(),
			order.orderedAt(),
			payment.paidAt()
		);
	}
}
