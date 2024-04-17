package com.hanghae.ecommerce.api.dto.response;

import com.hanghae.ecommerce.domain.cart.CartItem;
import com.hanghae.ecommerce.domain.product.Product;

public record UnitCartItemResult(
        Long cartItemId,
        Long productId,
        String productName,
        Long unitPrice,
        Long quantity,
        Long totalPrice
) {
    public static UnitCartItemResult of(CartItem cartItem, Product product) {
        return new UnitCartItemResult(
                cartItem.id(),
                product.id(),
                product.name(),
                product.price(),
                cartItem.quantity(),
                product.price() * cartItem.quantity()
        );
    }
}
