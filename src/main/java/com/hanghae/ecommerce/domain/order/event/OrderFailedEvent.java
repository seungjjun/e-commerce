package com.hanghae.ecommerce.domain.order.event;

import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.product.Stock;
import com.hanghae.ecommerce.storage.order.OrderStatus;

import java.util.List;

public record OrderFailedEvent(
		Order order,
		OrderStatus orderStatus,
		List<Stock> stocks,
		OrderRequest orderRequest) {
}
