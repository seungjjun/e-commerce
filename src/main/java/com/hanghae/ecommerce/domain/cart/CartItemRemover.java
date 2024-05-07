package com.hanghae.ecommerce.domain.cart;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hanghae.ecommerce.domain.user.User;

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

	public void resetCart(User user) {
		cartRepository.resetCart(user);
	}
}
