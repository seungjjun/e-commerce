package com.hanghae.ecommerce.api.dto.response;

public record DeleteCartItemResponse(String message) {
    public static DeleteCartItemResponse from(String message) {
        return new DeleteCartItemResponse(message);
    }
}
