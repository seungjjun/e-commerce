package com.hanghae.ecommerce.api.controller;

import com.hanghae.ecommerce.api.dto.request.CartItemRequest;
import com.hanghae.ecommerce.application.cart.CartUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart-items")
public class CartController {

    @ResponseStatus(HttpStatus.CREATED)
    public void addCartItems(@PathVariable Long userId,
                             @RequestBody CartItemRequest request) {
    }
}
