package com.hanghae.ecommerce.domain.order;

import org.springframework.stereotype.Component;

@Component
public class OrderReader {
    private final OrderRepository orderRepository;

    public OrderReader(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order readById(Long orderId) {
        return orderRepository.findById(orderId);
    }
}
