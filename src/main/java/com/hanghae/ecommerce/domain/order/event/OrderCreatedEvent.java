package com.hanghae.ecommerce.domain.order.event;

import com.hanghae.ecommerce.domain.order.Order;

public record OrderCreatedEvent(Order order) {
}
