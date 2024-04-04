package com.hanghae.ecommerce.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PaymentRequest(
        @NotNull Long orderId,
        @NotNull Long amount,
        @NotBlank String paymentMethod) {
}
