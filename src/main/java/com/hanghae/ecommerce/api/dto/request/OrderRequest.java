package com.hanghae.ecommerce.api.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequest(@Valid Receiver receiver,
                           @Valid List<ProductOrderRequest> products,
                           @NotNull Long paymentAmount) {
    public record ProductOrderRequest(
            @NotNull Long id,
            @NotNull Long quantity
    ) {
    }
}
