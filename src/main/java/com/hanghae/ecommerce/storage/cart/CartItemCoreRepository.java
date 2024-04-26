package com.hanghae.ecommerce.storage.cart;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.hanghae.ecommerce.domain.cart.Cart;
import com.hanghae.ecommerce.domain.cart.CartItem;
import com.hanghae.ecommerce.domain.cart.CartItemRepository;
import com.hanghae.ecommerce.domain.cart.NewCartItem;

@Repository
public class CartItemCoreRepository implements CartItemRepository {
	private final CartItemJpaRepository cartItemJpaRepository;

	public CartItemCoreRepository(CartItemJpaRepository cartItemJpaRepository) {
		this.cartItemJpaRepository = cartItemJpaRepository;
	}

	@Override
	public Optional<CartItemEntity> findByCartIdAndProductId(Long cartId, Long productId) {
		return cartItemJpaRepository.findByCartIdAndProductIdAndDeletedFalse(cartId, productId);
	}

	@Override
	public void addItem(Cart cart, NewCartItem cartItem) {
		cartItemJpaRepository.save(new CartItemEntity(cart.id(), cartItem.productId(), cartItem.quantity()));
	}

	@Override
	public void removeItems(List<Long> cartItemIds) {
		List<CartItemEntity> cartItemEntities = cartItemJpaRepository.findByIdIn(cartItemIds);
		cartItemEntities.forEach(CartItemEntity::delete);
	}

	@Override
	public List<CartItem> findAllByCartId(Long cartId) {
		return cartItemJpaRepository.findAllByCartId(cartId).stream()
			.filter(item -> !item.isDeleted())
			.sorted(Comparator.comparing(CartItemEntity::getCreatedAt).reversed())
			.map(CartItemEntity::toCartItem)
			.toList();
	}
}
