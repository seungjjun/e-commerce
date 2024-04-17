package com.hanghae.ecommerce.domain.cart;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartItemRemover {
    private final CartItemRepository cartItemRepository;

    public CartItemRemover(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public void removeItems(List<CartItem> cartItems) {
        cartItemRepository.removeItems(cartItems.stream()
                .map(CartItem::id)
                .toList()
        );
    }
}
