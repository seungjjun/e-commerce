package com.hanghae.ecommerce.domain.payment.event;

import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.payment.Payment;

public record PaymentSucceedEvent(Order order, Payment payment) {
}
