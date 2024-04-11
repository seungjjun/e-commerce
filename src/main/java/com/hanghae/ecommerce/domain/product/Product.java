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

    public void isEnoughProductStockQuantityForOrder(Long orderQuantity) {
        if (stockQuantity < orderQuantity) {
            throw new IllegalArgumentException(id + " 상품의 재고가 부족합니다.");
        }
    }

    public Product decreaseStock(Long quantity) {
        return new Product(id, name, price, description, stockQuantity - quantity);
    }

    public Product increaseStock(Long quantity) {
        return new Product(id, name, price, description, stockQuantity + quantity);
    }
}
