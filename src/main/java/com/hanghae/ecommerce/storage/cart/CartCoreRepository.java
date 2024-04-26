package com.hanghae.ecommerce.storage.cart;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.hanghae.ecommerce.domain.cart.Cart;
import com.hanghae.ecommerce.domain.cart.CartRepository;

@Repository
public class CartCoreRepository implements CartRepository {
	private final CartJpaRepository cartJpaRepository;

	public CartCoreRepository(CartJpaRepository cartJpaRepository) {
		this.cartJpaRepository = cartJpaRepository;
	}

	@Override
	public Cart findByUserId(Long userId) {
		Optional<CartEntity> cart = cartJpaRepository.findByUserId(userId);
		if (cart.isEmpty()) {
			return cartJpaRepository.save(new CartEntity(userId)).toCart();
		} else {
			return cart.get().toCart();
		}
	}
}
