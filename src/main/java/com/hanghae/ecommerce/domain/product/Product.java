package com.hanghae.ecommerce.domain.product;

public record Product(
        Long id,
        String name,
        Long price,
        String description,
        Long stockQuantity
) {
}
