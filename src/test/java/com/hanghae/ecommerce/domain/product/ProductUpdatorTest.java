package com.hanghae.ecommerce.domain.product;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.order.OrderItem;
import com.hanghae.ecommerce.storage.order.OrderStatus;

class ProductUpdatorTest {
	private ProductUpdator productUpdator;
	private ProductRepository productRepository;

	@BeforeEach
	void setUp() {
		productRepository = mock(ProductRepository.class);

		productUpdator = new ProductUpdator(productRepository);
	}

	@Test
	@DisplayName("상품의 재고를 업데이트 한다.")
	void update_product_stock() {
		// Given
		Product product = mock(Product.class);

		given(product.id()).willReturn(1L);

		OrderItem item = new OrderItem(
			1L, 1L,
			product.id(), product.name(),
			product.price(), product.orderTotalPrice(3L),
			3L, "CREATED");

		Order order = new Order(1L, 1L,
			50_000L, List.of(item),
			"이름", "주소",
			"번호", OrderStatus.PAID.toString(),
			"CARD", LocalDateTime.now());

		given(productRepository.findById(any())).willReturn(product);

		// When
		productUpdator.updateStock(order);

		// Then
		verify(productRepository, atLeast(1)).updateStock(any());
	}

}
