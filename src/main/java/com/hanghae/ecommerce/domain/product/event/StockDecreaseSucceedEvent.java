package com.hanghae.ecommerce.domain.product.event;

import com.hanghae.ecommerce.domain.order.Order;

public record StockDecreaseSucceedEvent(Order order) {
}
