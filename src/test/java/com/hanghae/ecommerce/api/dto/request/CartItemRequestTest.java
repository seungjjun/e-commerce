package com.hanghae.ecommerce.api.dto.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.hanghae.ecommerce.domain.cart.NewCartItem;

class CartItemRequestTest {
	@Test
	@DisplayName("장바구니에 추가할 상품이 0개인 경우 예외가 발생한다.")
	void when_add_to_cart_item_is_zero_then_throw_error() {
		// Given
		CartItemRequest cartItemRequest = new CartItemRequest(
			List.of(
				new CartItemRequest.CartItem(1L, 0L)
			)
		);

		// When && Then
		assertThrows(IllegalArgumentException.class, () -> {
			cartItemRequest.toNewCartItem();
		});
	}

	@Test
	@DisplayName("장바구니에 추가할 상품이 0 이상인 경우 예외가 발생하지 않는다.")
	void when_add_to_cart_item_is_not_zero_then_not_throw_error() {
		// Given
		CartItemRequest cartItemRequest = new CartItemRequest(
			List.of(
				new CartItemRequest.CartItem(1L, 1L)
			)
		);

		// When
		List<NewCartItem> newCartItem = cartItemRequest.toNewCartItem();

		// Then
		assertThat(newCartItem.size()).isEqualTo(1);
		assertThat(newCartItem.getFirst().productId()).isEqualTo(1L);
	}
}
