package com.hanghae.ecommerce.application.order;

import static org.assertj.core.api.Assertions.assertThat;

import com.hanghae.ecommerce.api.dto.response.CartItemResult;
import com.hanghae.ecommerce.domain.product.StockService;
import com.hanghae.ecommerce.domain.user.UserReader;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.api.dto.request.Receiver;
import com.hanghae.ecommerce.application.cart.CartUseCase;
import com.hanghae.ecommerce.domain.cart.NewCartItem;
import com.hanghae.ecommerce.domain.product.Stock;
import com.hanghae.ecommerce.domain.product.StockReader;
import com.hanghae.ecommerce.domain.user.User;
import com.hanghae.ecommerce.domain.user.UserService;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class OrderIntegrationTest {

	@Autowired
	private OrderUseCase orderUseCase;

	@Autowired
	private CartUseCase cartUseCase;

	@Autowired
	private UserService userService;

	@Autowired
	private StockReader stockReader;

	@Test
	void order_test() throws InterruptedException {
		// Given
		Long productId = 2L;
		Long userId = 1L;
		OrderRequest request = new OrderRequest(
			new Receiver(
				"홍길동",
				"서울특별시 송파구",
				"01012345678"
			),
			List.of(
				new OrderRequest.ProductRequest(productId, 1L)
			),
			100L,
			"CARD"
		);

		// When
		orderUseCase.order(userId, request);

		Thread.sleep(1000);

		// Then
		Stock stock = stockReader.readByProductId(productId);
		assertThat(stock.stockQuantity()).isEqualTo(10 - 1);
	}

	@Test
	@DisplayName("동시에 100건의 주문 테스트")
	void concurrency_100orders_test() throws InterruptedException {
		// Given
		int numThreads = 100;
		ExecutorService executor = Executors.newFixedThreadPool(numThreads);
		CountDownLatch latch = new CountDownLatch(numThreads);

		Long productId = 1L;
		Long userId = 1L;
		OrderRequest request = new OrderRequest(
			new Receiver(
				"홍길동",
				"서울특별시 송파구",
				"01012345678"
			),
			List.of(
				new OrderRequest.ProductRequest(productId, 1L)
			),
			100L,
			"CARD"
		);

		// When
		for (int i = 0; i < numThreads; i += 1) {
			executor.submit(() -> {
				try {
					orderUseCase.order(userId, request);
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();
		executor.shutdown();

		Thread.sleep(1500);

		// Then
		Stock stock = stockReader.readByProductId(productId);
		assertThat(stock.stockQuantity()).isEqualTo(0);

		User foundUser = userService.getUser(userId);
		assertThat(foundUser.point()).isEqualTo(100_000L - (100 * 100));
	}

	@Test
	@DisplayName("동시 5건 주문 시 포인트 차감 동시성 테스트")
	void when_concurrent_five_order_then_decrease_point() throws InterruptedException {
		// Given
		int numThreads = 5;
		ExecutorService executor = Executors.newFixedThreadPool(numThreads);
		CountDownLatch latch = new CountDownLatch(numThreads);

		Long productId = 1L;
		Long userId = 1L;
		OrderRequest request = new OrderRequest(
			new Receiver(
				"홍길동",
				"서울특별시 송파구",
				"01012345678"
			),
			List.of(
				new OrderRequest.ProductRequest(productId, 1L)
			),
			1000L,
			"CARD"
		);

		// When
		for (int i = 0; i < numThreads; i += 1) {
			executor.submit(() -> {
				try {
					orderUseCase.order(userId, request);
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();
		executor.shutdown();

		Thread.sleep(500);

		// Then
		User user = userService.getUser(userId);
		assertThat(user.point()).isEqualTo(100_000L - (1000L * numThreads));
	}

	@Test
	@DisplayName("재고 부족으로 인한 실패 시 재고 롤백 테스트")
	void when_not_enough_product_stock_then_roll_back_product_stock() {
		// Given
		Long userId = 1L;
		Long productId1 = 2L;
		Long productId2 = 3L;
		OrderRequest request1 = new OrderRequest(
			new Receiver(
				"홍길동",
				"서울특별시 송파구",
				"01012345678"
			),
			List.of(
				new OrderRequest.ProductRequest(productId1, 1L),
				new OrderRequest.ProductRequest(productId2, 10L)
			),
			1000L,
			"CARD"
		);

		// When
		orderUseCase.order(userId, request1);

		Stock stock = stockReader.readByProductId(productId1);
		assertThat(stock.stockQuantity()).isEqualTo(10L);
	}

	@Test
	@DisplayName("잔액 부족으로 인한 실패 시 재고 및 잔액 롤백 테스트")
	void when_not_enough_user_point_then_roll_back_user_point() throws InterruptedException {
		// Given
		Long paymentAmount = 999_999L;

		Long userId = 3L;
		Long productId = 2L;
		OrderRequest request = new OrderRequest(
			new Receiver(
				"홍길동",
				"서울특별시 송파구",
				"01012345678"
			),
			List.of(
				new OrderRequest.ProductRequest(productId, 1L)
			),
			paymentAmount,
			"CARD"
		);

		// When && Then
		orderUseCase.order(userId, request);

		Thread.sleep(1000);

		User user = userService.getUser(userId);
		assertThat(user.point()).isEqualTo(1_000L);

		Stock stock = stockReader.readByProductId(productId);
		assertThat(stock.stockQuantity()).isEqualTo(10L);
	}
}
