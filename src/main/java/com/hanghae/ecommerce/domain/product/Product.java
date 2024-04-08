package com.hanghae.ecommerce.domain.product;

public record Product(
        Long id,
        String name,
        Long price,
        String description,
        Long stockQuantity
) {
    public Long orderTotalPrice(Long quantity) {
        return price * quantity;
    }

    public boolean isRemainingProductStock(Long orderQuantity) {
        return stockQuantity >= orderQuantity;
    }
}
