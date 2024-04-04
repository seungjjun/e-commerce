package com.hanghae.ecommerce.api.dto.response;

public record ProductSummaryResponse(
        Long id,
        String name,
        Long price,
        String thumbnail
) {
}
