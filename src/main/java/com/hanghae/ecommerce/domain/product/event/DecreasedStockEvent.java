package com.hanghae.ecommerce.domain.product.event;

import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.product.Product;
import com.hanghae.ecommerce.domain.product.Stock;
import com.hanghae.ecommerce.domain.user.User;
import org.springframework.context.ApplicationEvent;

import java.util.List;

public record DecreasedStockEvent(
        User user,
        Order order,
        List<Product> products,
        OrderRequest orderRequests,
        List<Stock> decreasedStock
) {
}
