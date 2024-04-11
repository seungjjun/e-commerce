package com.hanghae.ecommerce.api.dto;

import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.payment.Payment;

public record OrderEventForStatistics(Order order, Payment payment)  {
}
