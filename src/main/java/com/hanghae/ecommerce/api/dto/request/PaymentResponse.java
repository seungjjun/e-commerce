package com.hanghae.ecommerce.api.dto.request;

public record PaymentResponse(
        String message,
        String status,
        String paidAt
) {
}
