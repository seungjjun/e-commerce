package com.hanghae.ecommerce.application.cart;

import com.hanghae.ecommerce.domain.cart.CartService;
import com.hanghae.ecommerce.domain.cart.NewCartItem;
import com.hanghae.ecommerce.domain.product.ProductService;
import com.hanghae.ecommerce.domain.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

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
}
