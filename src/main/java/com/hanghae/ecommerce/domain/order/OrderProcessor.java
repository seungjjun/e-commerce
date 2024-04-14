package com.hanghae.ecommerce.domain.order;

import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.domain.user.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderProcessor {

    private final OrderRepository orderRepository;

    public OrderProcessor(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order order(User user, OrderRequest request) {
        return orderRepository.order(user, request);
    }
}
