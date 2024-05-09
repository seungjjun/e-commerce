package com.hanghae.ecommerce.storage.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.hanghae.ecommerce.domain.product.Product;
import com.hanghae.ecommerce.domain.product.ProductService;
import com.hanghae.ecommerce.domain.product.ProductUpdator;
import com.redis.testcontainers.RedisContainer;

@SpringBootTest
@Testcontainers
public class ProductCacheIntegrationTest {

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductUpdator productUpdator;

	@Autowired
	private RedisTemplate<String, Product> redisTemplate;

	@Container
	public static RedisContainer redis = new RedisContainer("redis:latest");

	@BeforeEach
	void setUp() {
		redis.start();
	}

	@AfterEach
	void tearDown() {
		redis.stop();
	}

	@Test
	@DisplayName("캐싱된 상품을 조회한다.")
	public void get_cached_product() {
		Long productId = 1L;

		// 캐시 저장
		Product product = productService.getProductDetail(productId);

		// 캐시 데이터 조회
		Product cachedProduct = redisTemplate.opsForValue().get("products::" + productId);

		assertNotNull(cachedProduct);
		assertThat(product.name()).isEqualTo(cachedProduct.name());
	}

	@Test
	@DisplayName("상품 재고 업데이트 시 캐시 데이터가 업데이트 되는지 확인한다.")
	public void update_cache_product() {
		Long productId = 2L;

		// 캐시 저장
		productService.getProductDetail(productId);

		Product cachedProduct = redisTemplate.opsForValue().get("products::" + productId);
		assertNotNull(cachedProduct);

		Product decreasedStock = cachedProduct.decreaseStock(1L);
		// 캐시 업데이트
		productUpdator.updateProductStock(decreasedStock);
		Product updatedCacheProduct = redisTemplate.opsForValue().get("products::" + productId);

		// 캐시 업데이트 확인
		assertThat(updatedCacheProduct.stockQuantity()).isEqualTo(cachedProduct.stockQuantity() - 1);
	}
}
