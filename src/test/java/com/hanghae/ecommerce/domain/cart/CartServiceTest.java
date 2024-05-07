package com.hanghae.ecommerce.domain.cart;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.domain.product.Product;
import com.hanghae.ecommerce.domain.user.User;

class CartServiceTest {
	private CartFinder cartFinder;
	private CartItemFinder cartItemFinder;
	private CartItemAppender cartItemAppender;
	private CartItemRemover cartItemRemover;
	private CartService cartService;

	@BeforeEach
	void setUp() {
		cartFinder = mock(CartFinder.class);
		cartItemFinder = mock(CartItemFinder.class);
		cartItemAppender = mock(CartItemAppender.class);
		cartItemRemover = mock(CartItemRemover.class);

		cartService = new CartService(cartFinder, cartItemFinder, cartItemAppender, cartItemRemover);
	}

	@Test
	@DisplayName("사용자의 장바구니를 조회한다.")
	void getCart() {
		// Given
		User user = Fixtures.user(1L);
		Product product = Fixtures.product("후드티");
		Cart cart = new Cart(1L, user.id(), List.of(
			new CartItem(1L, product.id(), 5L)
		));

		given(cartFinder.findByUserId(any())).willReturn(cart);

		// When
		Cart foundCart = cartService.getCart(user);

		// Then
		assertThat(foundCart).isNotNull();
		assertThat(foundCart.userId()).isEqualTo(1L);
		assertThat(foundCart.items().size()).isEqualTo(1L);
		assertThat(foundCart.items().getFirst().quantity()).isEqualTo(5L);
	}

	@Test
	@DisplayName("장바구니에 아이템을 추가한다.")
	void add_item_to_cart() {
		// Given
		User user = Fixtures.user(1L);
		Product product = Fixtures.product("후드티");

		Cart cart = new Cart(1L, user.id(), List.of(
			new CartItem(1L, product.id(), 5L)
		));

		List<NewCartItem> newCartItems = List.of(
			new NewCartItem(1L, 1L)
		);

		// When
		cartService.addItemToCart(cart, newCartItems);

		// Then
		verify(cartItemAppender, atLeastOnce()).addItem(any(), anyList());
	}

	@Test
	@DisplayName("장바구니에 담긴 상품 중 선택한 상품만 조회한다.")
	void get_selected_cart_items() {
		// Given
		Long userId = 1L;

		List<Long> selectedCartItemIds = List.of(10L, 12L);

		List<CartItem> cartAllItem = List.of(
			new CartItem(10L, 1L, 1L),
			new CartItem(11L, 2L, 2L),
			new CartItem(12L, 3L, 3L),
			new CartItem(13L, 4L, 4L)
		);

		Cart cart = new Cart(1L, userId, cartAllItem);

		given(cartItemFinder.findAllByCartId(any())).willReturn(cartAllItem);

		// When
		List<CartItem> selectedCartItems = cartService.getCartItemsByIds(cart, selectedCartItemIds);

		// Then
		assertThat(selectedCartItems.size()).isEqualTo(2);
		assertThat(selectedCartItems.getFirst().id()).isEqualTo(10L);
		assertThat(selectedCartItems.getLast().id()).isEqualTo(12L);
	}

	@Test
	@DisplayName("장바구니에 담긴 모든 상품을 가져온다.")
	void get_all_cart_items() {
		// Given
		Long userId = 1L;
		Product product1 = Fixtures.product("후드티");
		Product product2 = Fixtures.product("맨투맨");
		List<CartItem> cartItems = List.of(
			new CartItem(1L, product1.id(), product1.stockQuantity()),
			new CartItem(1L, product2.id(), product2.stockQuantity())
		);

		Cart cart = new Cart(1L, userId, cartItems);

		given(cartItemFinder.findAllByCartId(any())).willReturn(cartItems);

		// When
		List<CartItem> foundCartItems = cartService.getAllCartItems(cart);

		// Then
		assertThat(foundCartItems.size()).isEqualTo(2);
		assertThat(foundCartItems.get(0).productId()).isEqualTo(1L);
		assertThat(foundCartItems.get(1).productId()).isEqualTo(2L);
	}

	@Test
	@DisplayName("장바구니의 상품을 삭제한다.")
	void delete_cart_items() {
		// Given
		List<CartItem> cartItems = List.of(
			new CartItem(1L, 1L, 1L),
			new CartItem(2L, 3L, 1L)
		);

		// When
		cartService.deleteItem(cartItems);

		// Then
		verify(cartItemRemover, atLeastOnce()).removeItems(any());
	}

	@Test
	@DisplayName("사용자의 장바구니를 초기화 한다.")
	void reset_cart_items() {
		// Given
		User user = Fixtures.user(1L);

		// When
		cartService.resetCart(user);

		// Then
		verify(cartItemRemover, atLeastOnce()).resetCart(any());
	}
}
