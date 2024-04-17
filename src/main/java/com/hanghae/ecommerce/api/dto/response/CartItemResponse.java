package com.hanghae.ecommerce.api.dto.response;

import java.util.List;

public record CartItemResponse(List<UnitCartItemResult> cartItems,
                               Long totalPrice) {
    public static CartItemResponse from(CartItemResult cartItemResult) {
        return new CartItemResponse(
                cartItemResult.cartItems(),
                cartItemResult.totalPrice()
        );
    }
}
