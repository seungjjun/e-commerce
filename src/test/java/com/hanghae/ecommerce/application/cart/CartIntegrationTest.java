package com.hanghae.ecommerce.application.cart;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.hanghae.ecommerce.api.dto.response.CartItemResult;
import com.hanghae.ecommerce.domain.cart.NewCartItem;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CartIntegrationTest {

	@Autowired
	private CartUseCase cartUseCase;

	@Test
	@DisplayName("장바구니 상품 조회, 추가, 삭제 통합 테스트")
	void cart_integration_test() {
		Long userId = 1L;
		Long productId = 1L;

		List<NewCartItem> newCartItems = List.of(
			new NewCartItem(productId, 1L)
		);

		// 장바구니에 상품 추가
		cartUseCase.addItem(userId, newCartItems);

		// 장바구니 상품 조회 (상품 추가 확인)
		CartItemResult addedCartItems = cartUseCase.getCartItems(userId);
		assertThat(addedCartItems.cartItems().size()).isEqualTo(1);

		// 장바구니 상품 제거
		cartUseCase.deleteItem(userId, List.of(productId));

		// 장바구니 상품 조회 (상품 제거 확인)
		CartItemResult deletedCartItem = cartUseCase.getCartItems(userId);
		assertThat(deletedCartItem.cartItems().size()).isEqualTo(0);
	}
}
