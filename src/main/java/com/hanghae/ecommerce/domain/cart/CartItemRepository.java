package com.hanghae.ecommerce.domain.cart;

import com.hanghae.ecommerce.storage.cart.CartItemEntity;

import java.util.Optional;

public interface CartItemRepository {
    Optional<CartItemEntity> findByCartIdAndProductId(Long cartId, Long productId);

    void addItem(Cart cart, NewCartItem cartItem);
}
