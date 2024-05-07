package com.hanghae.ecommerce.storage.cart;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanghae.ecommerce.domain.cart.Cart;
import com.hanghae.ecommerce.domain.cart.CartRepository;
import com.hanghae.ecommerce.domain.user.User;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CartCoreRepository implements CartRepository {
	private final CartJpaRepository cartJpaRepository;
	private final CartItemJpaRepository cartItemJpaRepository;

	@Override
	public Cart findByUserId(Long userId) {
		CartEntity cartEntity = cartJpaRepository.findByUserId(userId).orElseGet(() -> {
			CartEntity cart = new CartEntity(userId);
			cartJpaRepository.save(cart);
			return cart;
		});
		List<CartItemEntity> items = cartItemJpaRepository.findAllByCartId(cartEntity.getId())
			.stream().filter(item -> !item.isDeleted())
			.sorted(Comparator.comparing(CartItemEntity::getCreatedAt).reversed())
			.toList();
		return cartEntity.toCart(items);
	}

	@Override
	public void resetCart(Long userId) {
		CartEntity cartEntity = cartJpaRepository.findByUserId(userId).orElseGet(() -> {
			CartEntity cart = new CartEntity(userId);
			cartJpaRepository.save(cart);
			return cart;
		});

		cartItemJpaRepository.findAllByCartId(cartEntity.getId())
			.stream().filter(item -> !item.isDeleted())
			.forEach(CartItemEntity::delete);
	}
}
