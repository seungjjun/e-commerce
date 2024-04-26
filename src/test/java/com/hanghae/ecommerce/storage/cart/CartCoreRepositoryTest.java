package com.hanghae.ecommerce.storage.cart;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.domain.cart.Cart;
import com.hanghae.ecommerce.domain.user.User;

class CartCoreRepositoryTest {

	private CartJpaRepository cartJpaRepository;

	private CartCoreRepository cartCoreRepository;

	@BeforeEach
	void setUp() {
		cartJpaRepository = mock(CartJpaRepository.class);

		cartCoreRepository = new CartCoreRepository(cartJpaRepository);
	}

	@Test
	@DisplayName("사용자의 장바구니가 없는 경우 장바구니를 만든다.")
	void when_not_exist_user_cart_then_create_cart() {
		// Given
		User user = Fixtures.user(1L);

		CartEntity cartEntity = new CartEntity(user.id());
		given(cartJpaRepository.findByUserId(user.id())).willReturn(Optional.empty());
		given(cartJpaRepository.save(any())).willReturn(cartEntity);

		// When
		Cart newCart = cartCoreRepository.findByUserId(user.id());

		// Then
		assertThat(newCart).isNotNull();
		assertThat(newCart.userId()).isEqualTo(1L);
	}

}
