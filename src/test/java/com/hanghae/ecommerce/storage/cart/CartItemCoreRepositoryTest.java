package com.hanghae.ecommerce.storage.cart;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.domain.cart.Cart;
import com.hanghae.ecommerce.domain.cart.CartItem;
import com.hanghae.ecommerce.domain.cart.NewCartItem;
import com.hanghae.ecommerce.domain.product.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CartItemCoreRepositoryTest {

    @Autowired
    private CartItemCoreRepository cartItemCoreRepository;

    @Autowired
    private CartCoreRepository cartCoreRepository;

    @Test
    @Transactional
    @DisplayName("장바구니 상품 조회, 추가, 삭제 확인을 위한 통합 테스트")
    void testCartItemLifeCycle() {
        // 장바구니 조회
        Long userId = 1L;
        Product product = Fixtures.product("후드티");

        Cart cart = cartCoreRepository.findByUserId(userId);

        // 상품 추가
        NewCartItem newCartItem = new NewCartItem(product.id(), 1L);
        cartItemCoreRepository.addItem(cart, newCartItem);

        // 상품 조회
        List<CartItem> cartItems = cartItemCoreRepository.findAllByCartId(cart.id());

        assertThat(cartItems.size()).isEqualTo(1);
        assertThat(cartItems.get(0).productId()).isEqualTo(product.id());

        // 상품 삭제
        cartItemCoreRepository.removeItems(List.of(cartItems.get(0).id()));

        // 삭제되었는지 확인
        List<CartItem> deletedCartItems = cartItemCoreRepository.findAllByCartId(cart.id());
        assertThat(deletedCartItems.size()).isEqualTo(0);
    }
}
