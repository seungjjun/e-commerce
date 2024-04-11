package com.hanghae.ecommerce.api.controller;

import com.hanghae.ecommerce.api.dto.OrderPaidResult;
import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.api.dto.response.OrderResponse;
import com.hanghae.ecommerce.application.order.OrderUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Tag(name = "주문 및 결제 API", description = "상품을 주문 및 결제하는 API")
    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse order(@PathVariable Long userId,
                               @Valid @RequestBody OrderRequest request) {
        OrderPaidResult orderPaidResult = orderUseCase.order(userId, request);
        return OrderResponse.from(orderPaidResult);
    }
}
