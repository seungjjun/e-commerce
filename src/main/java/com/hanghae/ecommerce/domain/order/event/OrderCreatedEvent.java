package com.hanghae.ecommerce.domain.order.event;

import java.util.List;

import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.payment.Payment;
import com.hanghae.ecommerce.domain.product.Product;

public record OrderCreatedEvent(
	List<Product> products,
	List<OrderRequest.ProductOrderRequest> orderRequest,
	Order order,
	Payment payment
) {
}
