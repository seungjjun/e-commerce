package com.hanghae.ecommerce.api.dto.response;

import com.hanghae.ecommerce.domain.cart.CartItem;
import com.hanghae.ecommerce.domain.product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record CartItemResult(List<UnitCartItemResult> cartItems,
                             Long totalPrice) {
    public static CartItemResult of(List<CartItem> cartItems, List<Product> products) {
        Map<Long, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::id, product -> product));

        List<UnitCartItemResult> unitCartItemResults = cartItems.stream()
                .map(cartItem -> UnitCartItemResult.of(cartItem, productMap.get(cartItem.productId())))
                .collect(Collectors.toList());

        Long totalPrice = unitCartItemResults.stream()
                .mapToLong(UnitCartItemResult::totalPrice)
                .sum();

        return new CartItemResult(unitCartItemResults, totalPrice);
    }
}
