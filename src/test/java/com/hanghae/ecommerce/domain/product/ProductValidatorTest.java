package com.hanghae.ecommerce.domain.product;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.hanghae.ecommerce.api.error.InsufficientStockException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.domain.cart.NewCartItem;

class ProductValidatorTest {
	private ProductRepository productRepository;
	private ProductValidator productValidator;

	@BeforeEach
	void setUp() {
		productRepository = mock(ProductRepository.class);

		productValidator = new ProductValidator(productRepository);
	}

	@Test
	@DisplayName("장바구니에 담으려는 상품의 재고가 부족하지 않은 경우 예외가 발생하지 않는다.")
	void when_enough_product_stock_add_to_cart_then_not_throws_error() {
		// Given
		Long quantity = 1L;
		Product product = Fixtures.product("맨투맨");

		List<NewCartItem> cartItems = List.of(
			new NewCartItem(product.id(), quantity)
		);

		given(productRepository.findById(any())).willReturn(
			new Product(product.id(), product.name(), product.price(), product.description(), product.stockQuantity()));

		// When && Then
		assertDoesNotThrow(() -> {
			productValidator.checkPossibleAddToCart(cartItems);
		});
	}

	@Test
	@DisplayName("장바구니에 담으려는 상품의 재고가 부족한 경우 예외가 발생한다.")
	void when_not_enough_product_stock_add_to_cart_then_throws_error() {
		Long quantity = 1000L;
		Product product = Fixtures.product("맨투맨");

		List<NewCartItem> cartItems = List.of(
			new NewCartItem(product.id(), quantity)
		);

		given(productRepository.findById(any())).willReturn(
			new Product(product.id(), product.name(), product.price(), product.description(), product.stockQuantity()));

		// When && Then
		assertThrows(InsufficientStockException.class, () -> {
			productValidator.checkPossibleAddToCart(cartItems);
		});
	}
}
