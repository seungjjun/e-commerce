package com.hanghae.ecommerce.api.controller;

import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.api.dto.response.OrderResponse;
import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.order.OrderCoreService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("orders")
public class OrderController {
    private final OrderCoreService orderCoreService;

    public OrderController(OrderCoreService orderCoreService) {
        this.orderCoreService = orderCoreService;
    }

    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse order(@PathVariable Long userId,
                               @Valid @RequestBody OrderRequest request) {
        Order order = orderCoreService.order(userId, request);
        return OrderResponse.from(order);
    }
}
