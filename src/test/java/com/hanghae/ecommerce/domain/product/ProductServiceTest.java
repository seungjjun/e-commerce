package com.hanghae.ecommerce.domain.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.domain.cart.NewCartItem;
import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.storage.order.OrderStatus;

class ProductServiceTest {
	private ProductService productService;
	private ProductReader productReader;
	private ProductUpdator productUpdator;
	private ProductValidator productValidator;

	@BeforeEach
	void setUp() {
		productReader = mock(ProductReader.class);
		productUpdator = mock(ProductUpdator.class);
		productValidator = mock(ProductValidator.class);

		productService = new ProductService(productReader, productUpdator, productValidator);
	}

	@Test
	@DisplayName("상풍 목록을 조회한다.")
	void getProducts() {
		// Given
		Product product1 = Fixtures.product("후드티");
		Product product2 = Fixtures.product("맨투맨");

		given(productReader.readAll()).willReturn(List.of(product1, product2));

		// When
		List<Product> products = productService.getProducts();

		// Then
		assertThat(products).isNotNull();
		assertThat(products.size()).isEqualTo(2);
		assertThat(products.get(0).name()).isEqualTo("후드티");
		assertThat(products.get(1).name()).isEqualTo("맨투맨");
	}

	@Test
	@DisplayName("상품 상세 정보를 조회한다.")
	void getProductDetail() {
		// Given
		Long productId = 1L;

		Product product = Fixtures.product("후드티");

		given(productReader.readById(any())).willReturn(product);

		// When
		Product productDetail = productService.getProductDetail(productId);

		// Then
		assertThat(productDetail.name()).isEqualTo("후드티");
		assertThat(productDetail.stockQuantity()).isEqualTo(5L);
		assertThat(productDetail.description()).isEqualTo("그레이 후드티");
	}

	@Test
	@DisplayName("인기 상품을 조회한다.")
	void getPopularProducts() {
		// Given
		Product product1 = Fixtures.product("후드티");
		Product product2 = Fixtures.product("맨투맨");

		given(productReader.readPopularProducts()).willReturn(
			List.of(product1, product2)
		);

		// When
		List<Product> popularProducts = productService.getPopularProducts();

		// Then
		assertThat(popularProducts.size()).isEqualTo(2);
	}

	@Test
	@DisplayName("주문 요청 상품 id 기반으로 상품 목록을 조회한다.")
	void getOrderProductsByIds() {
		// Given
		List<Product> products = List.of(
			Fixtures.product("백팩"),
			Fixtures.product("후드티"),
			Fixtures.product("모자")
		);

		given(productReader.readAllByIds(anyList())).willReturn(products);

		// When
		List<Product> productList = productService.getProductsByIds(products.stream().map(Product::id).toList());

		// Then
		assertThat(productList.size()).isEqualTo(3);
		assertThat(productList.getFirst().name()).isEqualTo("백팩");
		assertThat(productList.getLast().name()).isEqualTo("모자");
	}

	@Test
	@DisplayName("장바구니에 상품을 담기 전 재고 검사 메서드 호출하는지 검사")
	void before_add_to_cart_check_product_stock_quantity() {
		// Given
		List<NewCartItem> cartItems = List.of(
			new NewCartItem(1L, 5L),
			new NewCartItem(2L, 10L)
		);

		// When
		productService.checkProductStockForAddToCart(cartItems);

		// Then
		verify(productValidator, atLeastOnce()).checkPossibleAddToCart(any());
	}

	@Test
	@DisplayName("상품 재고를 업데이트 하는 메서드를 호출하는지 검사")
	void verify_call_product_stock_update_method() {
		// Given
		Product product1 = Fixtures.product("후드티");
		Product product2 = Fixtures.product("맨투맨");
		List<Product> products = List.of(product1, product2);

		Order order = Fixtures.order(OrderStatus.READY);

		// When
		productService.updateStockQuantity(products, order);

		// Then
		verify(productUpdator, atLeastOnce()).updateStock(anyList(), any());
	}
}
