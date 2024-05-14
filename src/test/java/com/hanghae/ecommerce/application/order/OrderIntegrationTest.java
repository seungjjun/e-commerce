package com.hanghae.ecommerce.application.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import com.hanghae.ecommerce.api.dto.response.CartItemResult;
import com.hanghae.ecommerce.application.cart.CartUseCase;
import com.hanghae.ecommerce.domain.cart.NewCartItem;
import com.hanghae.ecommerce.domain.order.OrderItemReader;
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

	@Autowired
	private OrderItemReader orderItemReader;

	private Long userId1;
	private Long userId2;
	private Long userId3;
	private Long productId;

	@BeforeEach
	void setUp() {
		userId1 = 1L;
		userId2 = 2L;
		userId3 = 3L;

		productId = 2L;
		Long orderQuantity = 1L;

		cartUseCase.addItem(userId1, List.of(new NewCartItem(productId, orderQuantity)));
		cartUseCase.addItem(userId2, List.of(new NewCartItem(productId, orderQuantity)));
		cartUseCase.addItem(userId3, List.of(
			new NewCartItem(productId, orderQuantity),
			new NewCartItem(productId, 10L)
		));
	}

	@Test
	@DisplayName("동시에 2건의 주문 시 재고 감소 테스트")
	void when_concurrent_two_order_then_decrease_stock() throws InterruptedException {
		// Given
		int numThreads = 2;
		ExecutorService executor = Executors.newFixedThreadPool(numThreads);
		CountDownLatch latch = new CountDownLatch(numThreads);

		OrderRequest request = new OrderRequest(
			new Receiver(
				"홍길동",
				"서울특별시 송파구",
				"01012345678"
			),
			1000L,
			"CARD"
		);

		// When
		executor.submit(() -> {
			try {
				orderUseCase.order(userId1, request);
			} finally {
				latch.countDown();
			}
		});

		executor.submit(() -> {
			try {
				orderUseCase.order(userId2, request);
			} finally {
				latch.countDown();
			}
		});

		latch.await();
		executor.shutdown();

		// Then

		Stock stock = stockReader.readByProductId(productId);
		assertThat(stock.stockQuantity()).isEqualTo(10L - 1L - 1L);

		User user = userService.getUser(userId1);
		assertThat(user.point()).isEqualTo(100_000L - 1000L);
	}

	@Test
	@DisplayName("동시 5건 주문 시 포이트 차감 동시성 테스트")
	void when_concurrent_five_order_then_decrease_point() throws InterruptedException {
		// Given
		int numThreads = 5;
		ExecutorService executor = Executors.newFixedThreadPool(numThreads);
		CountDownLatch latch = new CountDownLatch(numThreads);

		OrderRequest request = new OrderRequest(
			new Receiver(
				"홍길동",
				"서울특별시 송파구",
				"01012345678"
			),
			1000L,
			"CARD"
		);

		// When
		for (int i = 0; i < numThreads; i += 1) {
			executor.submit(() -> {
				try {
					orderUseCase.order(userId1, request);
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();
		executor.shutdown();

		// Then
		User user = userService.getUser(userId1);
		assertThat(user.point()).isEqualTo(100_000L - (1000L * numThreads));
	}

	@Test
	@DisplayName("재고 부족으로 인한 실패 시 재고 롤백 테스트")
	void when_not_enough_product_stock_then_roll_back_product_stock() {
		// Given
		OrderRequest request = new OrderRequest(
			new Receiver(
				"홍길동",
				"서울특별시 송파구",
				"01012345678"
			),
			1000L,
			"CARD"
		);

		// When
		assertThrows(RuntimeException.class, () -> {
			orderUseCase.order(userId3, request);
		});

		Stock stock = stockReader.readByProductId(productId);
		assertThat(stock.stockQuantity()).isEqualTo(10L);
	}

	@Test
	@DisplayName("잔액 부족으로 인한 실패 시 재고 및 잔액 롤백 테스트")
	void when_not_enough_user_point_then_roll_back_user_point() {
		// Given
		Long paymentAmount = 999_999L;

		OrderRequest request = new OrderRequest(
			new Receiver(
				"홍길동",
				"서울특별시 송파구",
				"01012345678"
			),
			paymentAmount,
			"CARD"
		);

		// When && Then
		assertThrows(RuntimeException.class, () -> {
			orderUseCase.order(userId1, request);
		});

		User user = userService.getUser(userId1);
		assertThat(user.point()).isEqualTo(100_000L);

		Stock stock = stockReader.readByProductId(productId);
		assertThat(stock.stockQuantity()).isEqualTo(10L);
	}

	@Test
	@DisplayName("결제 실패로 인한 상품 주문 실패 시 장바구니 롤백 테스트")
	void when_failed_order_then_roll_back_cart_item() {
		// Given
		Long paymentAmount = 999_999L;

		OrderRequest request = new OrderRequest(
			new Receiver(
				"홍길동",
				"서울특별시 송파구",
				"01012345678"
			),
			paymentAmount,
			"CARD"
		);

		CartItemResult initCart = cartUseCase.getCartItems(userId1);
		assertThat(initCart.cartItems().isEmpty()).isFalse();
		assertThat(initCart.cartItems().size()).isEqualTo(1);

		// When
		assertThrows(RuntimeException.class, () -> {
			orderUseCase.order(userId1, request);
		});

		// Then
		CartItemResult afterOrderFailedCart = cartUseCase.getCartItems(userId1);
		assertThat(afterOrderFailedCart.cartItems().isEmpty()).isFalse();
		assertThat(initCart.cartItems().size()).isEqualTo(1);
	}
}
