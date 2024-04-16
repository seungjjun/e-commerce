package com.hanghae.ecommerce.domain.cart;

import org.springframework.stereotype.Component;

@Component
public class CartFinder {
    private final CartRepository cartRepository;

    public CartFinder(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart findByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }
}
