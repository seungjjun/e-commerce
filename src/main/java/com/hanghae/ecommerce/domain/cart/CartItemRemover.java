package com.hanghae.ecommerce.domain.cart;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CartItemRemover {
	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;

	public void removeItems(List<CartItem> cartItems) {
		cartItemRepository.removeItems(cartItems.stream()
			.map(CartItem::id)
			.toList()
		);
	}

	public void resetCart(Long userId) {
		cartRepository.resetCart(userId);
	}
}
