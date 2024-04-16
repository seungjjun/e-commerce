package com.hanghae.ecommerce.domain.cart;

public record CartItem(Long id, Long cartId, Long productId, Long quantity) {
}
