package com.hanghae.ecommerce.api.dto.response;

import com.hanghae.ecommerce.domain.product.Product;

public record ProductSummaryResponse(
        Long id,
        String name,
        Long price) {
    public static ProductSummaryResponse from(Product product) {
        return new ProductSummaryResponse(
                product.id(),
                product.name(),
                product.price()
        );
    }
}
