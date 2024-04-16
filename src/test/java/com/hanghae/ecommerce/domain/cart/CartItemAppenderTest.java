package com.hanghae.ecommerce.domain.cart;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.domain.product.Product;
import com.hanghae.ecommerce.storage.cart.CartItemEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class CartItemAppenderTest {
    private CartItemRepository cartItemRepository;

    private CartItemAppender cartItemAppender;

    @BeforeEach
    void setUp() {
        cartItemRepository = mock(CartItemRepository.class);

        cartItemAppender = new CartItemAppender(cartItemRepository);
    }

    @Test
    @DisplayName("이미 장바구니 추가된 상품을 추가할 경우 아이템 수량을 증가시키는 메서드가 호출된다.")
    void when_already_added_cart_then_add_quantity_cart_item() {
        // Given
        Cart cart = new Cart(1L, 1L);
        Product product = Fixtures.product("맨투맨");

        List<NewCartItem> cartItems = List.of(
                new NewCartItem(product.id(), 1L)
        );

        CartItemEntity cartItemEntity = mock(CartItemEntity.class);

        given(cartItemRepository.findByCartIdAndProductId(any(), any())).willReturn(Optional.of(cartItemEntity));

        // When
        cartItemAppender.addItem(cart, cartItems);

        // Then
        verify(cartItemEntity, atLeastOnce()).addQuantity(any());
    }

    @Test
    @DisplayName("장바구니에 존재하지 않는 상품을 추가할 경우 장바구니 아이템을 생성한다.")
    void when_not_exists_cart_item_then_create_cart_item() {
        // Given
        Cart cart = new Cart(1L, 1L);
        Product product = Fixtures.product("맨투맨");

        List<NewCartItem> cartItems = List.of(
                new NewCartItem(product.id(), 1L)
        );

        given(cartItemRepository.findByCartIdAndProductId(any(), any())).willReturn(Optional.empty());

        // When
        cartItemAppender.addItem(cart, cartItems);

        // Then
        verify(cartItemRepository, atLeastOnce()).addItem(any(), any());
    }

    @Test
    @DisplayName("장바구니에 여러 상품을 추가할 경우 해당 상품의 수 만큼 장바구니 아이템을 생성한다.")
    void when_add_many_product_to_cart_then_call_create_cart_item() {
        Cart cart = new Cart(1L, 1L);
        Product product = Fixtures.product("맨투맨");
        Product product2 = Fixtures.product("슬랙스");
        Product product3 = Fixtures.product("백팩");

        List<NewCartItem> cartItems = List.of(
                new NewCartItem(product.id(), 1L),
                new NewCartItem(product2.id(), 1L),
                new NewCartItem(product3.id(), 1L)
        );

        given(cartItemRepository.findByCartIdAndProductId(any(), any())).willReturn(Optional.empty());

        // When
        cartItemAppender.addItem(cart, cartItems);

        // Then
        verify(cartItemRepository, times(3)).addItem(any(), any());
    }
}
