package com.hanghae.ecommerce.domain.cart;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CartItemFinderTest {
	private CartItemRepository cartItemRepository;
	private CartItemFinder cartItemFinder;

	@BeforeEach
	void setUp() {
		cartItemRepository = mock(CartItemRepository.class);

		cartItemFinder = new CartItemFinder(cartItemRepository);
	}

	@Test
	@DisplayName("장바구니 id를 이용해 장바구니에 담긴 상품을 조회한다.")
	void find_all_cart_items() {
		// Given
		Long cartId = 1L;
		Long productId = 1L;
		Long quantity = 1L;

		List<CartItem> cartItems = List.of(
			new CartItem(1L, productId, quantity)
		);

		given(cartItemRepository.findAllByCartId(any())).willReturn(cartItems);

		// When
		List<CartItem> foundCartItems = cartItemFinder.findAllByCartId(cartId);

		// Then
		assertThat(foundCartItems.size()).isEqualTo(1);
	}
}
