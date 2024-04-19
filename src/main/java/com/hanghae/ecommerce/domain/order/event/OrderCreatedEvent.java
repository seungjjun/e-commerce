package com.hanghae.ecommerce.domain.order.event;

import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.payment.Payment;
import com.hanghae.ecommerce.domain.product.Product;
import com.hanghae.ecommerce.domain.user.User;

import java.util.List;

public record OrderCreatedEvent(
        User user,
        List<Product> products,
        OrderRequest orderRequest,
        Order order
)  {
}
