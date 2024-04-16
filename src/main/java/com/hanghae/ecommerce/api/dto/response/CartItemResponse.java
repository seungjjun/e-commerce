package com.hanghae.ecommerce.api.dto.response;

public record CartItemResponse(String message) {
    public static CartItemResponse from(String message) {
        return new CartItemResponse(message);
    }
}
