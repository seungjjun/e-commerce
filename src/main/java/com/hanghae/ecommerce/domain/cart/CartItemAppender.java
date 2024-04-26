package com.hanghae.ecommerce.domain.cart;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.hanghae.ecommerce.storage.cart.CartItemEntity;

@Component
public class CartItemAppender {
	private final CartItemRepository cartItemRepository;

	public CartItemAppender(CartItemRepository cartItemRepository) {
		this.cartItemRepository = cartItemRepository;
	}

	public void addItem(Cart cart, List<NewCartItem> newCartItems) {
		for (NewCartItem cartItem : newCartItems) {
			Optional<CartItemEntity> cartItemEntity =
				cartItemRepository.findByCartIdAndProductId(cart.id(), cartItem.productId());

			if (cartItemEntity.isEmpty()) {
				cartItemRepository.addItem(cart, cartItem);
			} else {
				cartItemEntity.get().addQuantity(cartItem.quantity());
			}
		}
	}
}
