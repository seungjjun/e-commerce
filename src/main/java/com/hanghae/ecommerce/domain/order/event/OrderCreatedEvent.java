package com.hanghae.ecommerce.domain.order.event;

import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.payment.Payment;

public record OrderCreatedEvent(
	Order order,
	Payment payment
) {
}
