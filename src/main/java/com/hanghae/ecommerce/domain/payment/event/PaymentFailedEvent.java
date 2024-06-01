package com.hanghae.ecommerce.domain.payment.event;

import com.hanghae.ecommerce.domain.order.Order;

public record PaymentFailedEvent(Order order) {
}
