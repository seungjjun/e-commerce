package com.hanghae.ecommerce.api.controller;

import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.api.dto.request.Receiver;
import com.hanghae.ecommerce.api.dto.response.OrderResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("orders")
public class OrderController {

    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse order(@PathVariable Long userId,
                               @Valid @RequestBody OrderRequest request) {
        return new OrderResponse(
                1L,
                new Receiver(
                        "홍길동",
                        "서울시 송파구",
                        "01012345678"
                ),
                10_800L,
                "2024-04-02 13:00:00",
                "COMPLETED");
    }
}
