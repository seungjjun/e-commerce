package com.hanghae.ecommerce.domain.cart;

public interface CartRepository {
    Cart findByUserId(Long userId);
}
