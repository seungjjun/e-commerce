package com.hanghae.ecommerce.domain.cart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartItemRemoverTest {

    private CartItemRepository cartItemRepository;

    private CartItemRemover cartItemRemover;

    @BeforeEach
    void setUp() {
        cartItemRepository = mock(CartItemRepository.class);

        cartItemRemover = new CartItemRemover(cartItemRepository);
    }

    @Test
    @DisplayName("장바구니 상품을 삭제한다.")
    void remove_cart_items() {
        // Given
        List<CartItem> cartItems = List.of(
                new CartItem(1L, 1L, 1L, 1L),
                new CartItem(2L, 1L, 2L, 1L)
        );

        // When
        cartItemRemover.removeItems(cartItems);

        // Then
        verify(cartItemRepository, atLeastOnce()).removeItems(any());
    }
}
