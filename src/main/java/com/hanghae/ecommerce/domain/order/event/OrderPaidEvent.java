package com.hanghae.ecommerce.domain.order.event;

import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.payment.Payment;
import com.hanghae.ecommerce.domain.product.Product;

import java.util.List;

public record OrderPaidEvent(Order order, Payment payment,
                             List<Product> products,
                             OrderRequest orderRequest) {
}
