package com.hanghae.ecommerce.domain.cart;

import java.util.List;
import java.util.Optional;

import com.hanghae.ecommerce.storage.cart.CartItemEntity;

public interface CartItemRepository {
	Optional<CartItemEntity> findByCartIdAndProductId(Long cartId, Long productId);

	void addItem(Cart cart, NewCartItem cartItem);

	void removeItems(List<Long> cartItemIds);

	List<CartItem> findAllByCartId(Long cartId);
}
