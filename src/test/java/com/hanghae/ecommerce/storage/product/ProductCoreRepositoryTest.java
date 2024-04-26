package com.hanghae.ecommerce.storage.product;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.domain.product.Product;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductCoreRepositoryTest {
	@Autowired
	private ProductCoreRepository productCoreRepository;

	@Test
	@Transactional
	@DisplayName("상품 조회, 업데이트, 재고 확인을 통합 테스트")
	void testProductLifecycle() {
		// 상품 조회
		Long productId = 1L;
		Product product = productCoreRepository.findById(productId).get().toProduct();
		assertThat(product.name()).isEqualTo("후드티");
		assertThat(product.stockQuantity()).isEqualTo(5L);

		List<Product> products = productCoreRepository.findByIdIn(List.of(productId));
		assertThat(products.size()).isEqualTo(1);
		assertThat(products.get(0).name()).isEqualTo("후드티");

		List<Product> productAll = productCoreRepository.findAll();
		assertThat(productAll.size()).isEqualTo(6);


		// 재고 업데이트
		Product updatedStock = product.updateStock(3L);
		productCoreRepository.updateStock(updatedStock);

		// 업데이트된 재고 확인
		product = productCoreRepository.findById(productId).get().toProduct();
		assertThat(product.stockQuantity()).isEqualTo(3L);
	}
}
