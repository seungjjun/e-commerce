package com.hanghae.ecommerce.domain.cart;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.domain.user.User;

class CartItemRemoverTest {

	private CartRepository cartRepository;
	private CartItemRepository cartItemRepository;

	private CartItemRemover cartItemRemover;

	@BeforeEach
	void setUp() {
		cartRepository = mock(CartRepository.class);
		cartItemRepository = mock(CartItemRepository.class);

		cartItemRemover = new CartItemRemover(cartRepository, cartItemRepository);
	}

	@Test
	@DisplayName("장바구니 상품을 삭제한다.")
	void remove_cart_items() {
		// Given
		List<CartItem> cartItems = List.of(
			new CartItem(1L, 1L, 1L),
			new CartItem(2L, 2L, 1L)
		);

		// When
		cartItemRemover.removeItems(cartItems);

		// Then
		verify(cartItemRepository, atLeastOnce()).removeItems(any());
	}

	@Test
	@DisplayName("사용자의 장바구니를 초기화 한다.")
	void reset_cart() {
		// Given
		User user = Fixtures.user(1L);

		// When
		cartItemRemover.resetCart(user);

		// Then
		verify(cartRepository, atLeastOnce()).resetCart(any());
	}
}
