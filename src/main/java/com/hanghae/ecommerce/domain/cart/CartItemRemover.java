package com.hanghae.ecommerce.domain.cart;

import java.util.List;

import org.springframework.stereotype.Component;

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
