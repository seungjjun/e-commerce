package com.hanghae.ecommerce.api.dto.response;

import com.hanghae.ecommerce.domain.product.Product;

public record ProductDetailResponse(
        Long id,
        String name,
        Long price,
        Long stockQuantity,
        String description
) {
    public static ProductDetailResponse from(Product product) {
        return new ProductDetailResponse(
                product.id(),
                product.name(),
                product.price(),
                product.stockQuantity(),
                product.description()
        );
    }
}
