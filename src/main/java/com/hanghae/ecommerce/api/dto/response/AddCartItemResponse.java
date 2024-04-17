package com.hanghae.ecommerce.api.dto.response;

public record AddCartItemResponse(String message) {
    public static AddCartItemResponse from(String message) {
        return new AddCartItemResponse(message);
    }
}
