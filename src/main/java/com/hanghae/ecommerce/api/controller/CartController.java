package com.hanghae.ecommerce.api.controller;

import com.hanghae.ecommerce.api.dto.request.CartItemRequest;
import com.hanghae.ecommerce.api.dto.response.CartItemResponse;
import com.hanghae.ecommerce.application.cart.CartUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart-items")
public class CartController {

    private final CartUseCase cartUseCase;

    public CartController(CartUseCase cartUseCase) {
        this.cartUseCase = cartUseCase;
    }

    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CartItemResponse addCartItems(@PathVariable Long userId,
                                         @RequestBody CartItemRequest request) {
        cartUseCase.addItem(userId, request.toNewCartItem());
        return CartItemResponse.from("상품이 추가 되었습니다.");
    }
}
