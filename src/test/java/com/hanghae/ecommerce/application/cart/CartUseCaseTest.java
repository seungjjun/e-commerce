package com.hanghae.ecommerce.application.cart;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.api.dto.response.CartItemResult;
import com.hanghae.ecommerce.domain.cart.Cart;
import com.hanghae.ecommerce.domain.cart.CartItem;
import com.hanghae.ecommerce.domain.cart.CartService;
import com.hanghae.ecommerce.domain.cart.NewCartItem;
import com.hanghae.ecommerce.domain.product.Product;
import com.hanghae.ecommerce.domain.product.ProductService;
import com.hanghae.ecommerce.domain.user.User;
import com.hanghae.ecommerce.domain.user.UserService;

class CartUseCaseTest {
	private UserService userService;
	private CartService cartService;
	private ProductService productService;

	private CartUseCase cartUseCase;

	@BeforeEach
	void setUp() {
		userService = mock(UserService.class);
		cartService = mock(CartService.class);
		productService = mock(ProductService.class);

		cartUseCase = new CartUseCase(userService, cartService, productService);
	}

	@Test
	@DisplayName("상품을 장바구니에 추가한다.")
	void addItemToCart() {
		// Given
		Long userId = 1L;
		Long productId = 1L;
		List<NewCartItem> cartItems = List.of(
			new NewCartItem(productId, 1L)
		);

		// When
		cartUseCase.addItem(userId, cartItems);

		// Then
		verify(productService, atLeastOnce()).checkProductStockForAddToCart(any());
		verify(cartService, atLeastOnce()).addItemToCart(any(), anyList());
	}

	@Test
	@DisplayName("장바구니 상품을 삭제한다.")
	void deleteCartItems() {
		// Given
		Long userId = 1L;
		List<Long> cartItemIds = List.of();

		User user = Fixtures.user(userId);

		given(userService.getUser(any())).willReturn(user);
		given(cartService.getCart(any())).willReturn(new Cart(1L, user.id()));
		given(cartService.getCartItemsByIds(any(), any())).willReturn(List.of(
			new CartItem(1L, 1L, 1L, 1L)
		));

		// When
		cartUseCase.deleteItem(userId, cartItemIds);

		// Then
		verify(cartService, atLeastOnce()).deleteItem(any());
	}

	@Test
	@DisplayName("장바구니에 추가된 상품을 조회한다.")
	void getCartItems() {
		// Given
		Long userId = 1L;

		User user = Fixtures.user(userId);

		Product product1 = Fixtures.product("백팩");
		Product product2 = Fixtures.product("모자");

		Cart cart = new Cart(1L, userId);
		List<CartItem> cartItems = List.of(
			new CartItem(1L, cart.id(), product1.id(), 1L),
			new CartItem(1L, cart.id(), product2.id(), 2L)
		);
		List<Product> products = List.of(
			product1,
			product2
		);

		given(userService.getUser(any())).willReturn(user);
		given(cartService.getCart(any())).willReturn(cart);
		given(cartService.getAllCartItems(any())).willReturn(cartItems);
		given(productService.getProductsByIds(any())).willReturn(products);

		// When
		CartItemResult foundCartItems = cartUseCase.getCartItems(userId);

		// Then
		assertThat(foundCartItems).isNotNull();
		assertThat(foundCartItems.cartItems().size()).isEqualTo(2);
		assertThat(foundCartItems.cartItems().getFirst().productName()).isEqualTo("백팩");
		assertThat(foundCartItems.cartItems().getFirst().unitPrice()).isEqualTo(60_000L);
		assertThat(foundCartItems.cartItems().getLast().productName()).isEqualTo("모자");
		assertThat(foundCartItems.cartItems().getLast().unitPrice()).isEqualTo(15_000L);
		assertThat(foundCartItems.totalPrice()).isEqualTo(60_000L + 15_000L + 15_000L);
	}
}
