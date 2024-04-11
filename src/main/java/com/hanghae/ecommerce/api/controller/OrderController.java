package com.hanghae.ecommerce.api.controller;

import com.hanghae.ecommerce.api.dto.OrderPaidResult;
import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.api.dto.response.OrderResponse;
import com.hanghae.ecommerce.application.order.OrderUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("orders")
public class OrderController {
    private final OrderUseCase orderUseCase;

    public OrderController(OrderUseCase orderUseCase) {
        this.orderUseCase = orderUseCase;
    }

    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse order(@PathVariable Long userId,
                               @Valid @RequestBody OrderRequest request) {
        OrderPaidResult orderPaidResult = orderUseCase.order(userId, request);
        return OrderResponse.from(orderPaidResult);
    }
}
