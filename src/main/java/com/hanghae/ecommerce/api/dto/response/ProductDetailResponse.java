package com.hanghae.ecommerce.api.dto.response;

public record ProductDetailResponse(
        Long id,
        String name,
        Long price,
        Long stockQuantity,
        String description
) {
}
