package com.hanghae.ecommerce.domain.cart;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.domain.product.Product;

class CartFinderTest {
	private CartRepository cartRepository;

	private CartFinder cartFinder;

	@BeforeEach
	void setUp() {
		cartRepository = mock(CartRepository.class);

		cartFinder = new CartFinder(cartRepository);
	}

	@Test
	@DisplayName("사용자의 장바구니를 조회한다.")
	void findCartByUserId() {
		// Given
		Long userId = 1L;
		Product product = Fixtures.product("후드티");
		Cart cart = new Cart(1L, userId, List.of(
			new CartItem(1L, product.id(), 5L)
		));

		given(cartRepository.findByUserId(any())).willReturn(cart);

		// When
		Cart foundCart = cartFinder.findByUserId(userId);

		// Then
		assertThat(foundCart.userId()).isEqualTo(1L);
	}

}
