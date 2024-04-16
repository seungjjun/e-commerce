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
    private CartItemAppender cartItemAppender;
    private CartService cartService;

    @BeforeEach
    void setUp() {
        cartFinder = mock(CartFinder.class);
        cartItemAppender = mock(CartItemAppender.class);

        cartService = new CartService(cartFinder, cartItemAppender);
    }

    @Test
    @DisplayName("사용자의 장바구니를 조회한다.")
    void getCart() {
        // Given
        User user = Fixtures.user(1L);

        given(cartFinder.findByUserId(any())).willReturn(new Cart(1L,  user.id()));

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
}
