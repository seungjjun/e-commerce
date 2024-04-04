package com.hanghae.ecommerce.api.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record OrderRequest(Receiver receiver,
                           List<ProductOrderRequest> products) {
    public record ProductOrderRequest(
            @NotBlank Long id,
            @NotBlank Long quantity
    ) {
    }
}
