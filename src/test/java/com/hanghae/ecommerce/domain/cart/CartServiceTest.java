package com.hanghae.ecommerce.domain.cart;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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

        given(cartFinder.findByUserId(any())).willReturn(new Cart(1L, user.id()));

        // When
        Cart cart = cartService.getCart(user);

        // Then
        assertThat(cart).isNotNull();
        assertThat(cart.userId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("장바구니에 아이템을 추가한다.")
    void add_item_to_cart() {
        // Given
        User user = Fixtures.user(1L);

        Cart cart = new Cart(1L, user.id());
        List<NewCartItem> newCartItems = List.of(
                new NewCartItem(1L, 1L)
        );

        // When
        cartService.addItemToCart(cart, newCartItems);

        // Then
        verify(cartItemAppender, atLeastOnce()).addItem(any(), anyList());
    }

    @Test
    @DisplayName("장바구니에 담긴 상품을 조회한다.")
    void get_cart_items() {
        // Given
        Long userId = 1L;
        Cart cart = new Cart(1L, userId);

        List<Long> selectedCartItemIds = List.of(10L, 12L);

        List<CartItem> cartAllItem = List.of(
                new CartItem(10L, cart.id(), 1L, 1L),
                new CartItem(11L, cart.id(), 2L, 1L),
                new CartItem(12L, cart.id(), 3L, 1L),
                new CartItem(13L, cart.id(), 4L, 1L)
        );

        given(cartItemFinder.findAllByCartId(any())).willReturn(cartAllItem);

        // When
        List<CartItem> selectedCartItems = cartService.getCartItemsByIds(cart, selectedCartItemIds);

        // Then
        assertThat(selectedCartItems.size()).isEqualTo(2);
        assertThat(selectedCartItems.getFirst().id()).isEqualTo(10L);
        assertThat(selectedCartItems.getLast().id()).isEqualTo(12L);
    }

    @Test
    @DisplayName("장바구니의 상품을 삭제한다.")
    void delete_cart_items() {
        // Given
        List<CartItem> cartItems = List.of(
                new CartItem(1L, 1L, 1L, 1L),
                new CartItem(2L, 1L, 3L, 1L)
        );

        // When
        cartService.deleteItem(cartItems);

        // Then
        verify(cartItemRemover, atLeastOnce()).removeItems(any());
    }
}
