package com.hanghae.ecommerce.domain.cart;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CartItemFinder {
	private final CartItemRepository cartItemRepository;

	public CartItemFinder(CartItemRepository cartItemRepository) {
		this.cartItemRepository = cartItemRepository;
	}

	public List<CartItem> findAllByCartId(Long cartId) {
		return cartItemRepository.findAllByCartId(cartId);
	}
}
