package com.hanghae.ecommerce.application.order;

import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.api.dto.request.Receiver;
import com.hanghae.ecommerce.domain.product.Product;
import com.hanghae.ecommerce.domain.product.ProductService;
import com.hanghae.ecommerce.domain.user.User;
import com.hanghae.ecommerce.domain.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class OrderUseCaseIntegrationTest {

    @Autowired
    private OrderUseCase orderUseCase;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    /**
     * 재고가 5개인 id 값이 1인 상품을 5000원을 소지하고 있는 사용자가 동시에 5번 주문 요청을 했을 때, 정확히 5개의 재고가 감소하고 5000원이 모두 소진되는 것을 테스트 합니다.
     * 1. 상품 아이디 값 1 과 주문량 1을 request로 요청합니다.
     * 2. 동시에 5번 order 메서드 호출
     * 3. 사용자의 포인트가 0원 인 것을 확인합니다.
     * 4. 1번 상품의 재고가 0인 것을 확인합니다.
     * @throws InterruptedException
     */
    @Test
    @DisplayName("주문 동시성 테스트")
    void concurrency_order_test() throws InterruptedException {
        // Given
        int numThreads = 5;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        CountDownLatch latch = new CountDownLatch(numThreads);

        Long userId = 1L;
        Long productId = 1L;
        OrderRequest request = new OrderRequest(
                new Receiver(
                        "홍길동",
                        "서울특별시 송파구",
                        "01012345678"
                ),
                List.of(
                        new OrderRequest.ProductOrderRequest(productId, 1L)
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

        User foundUser = userService.getUser(userId);
        Product product = productService.getProductDetail(productId);

        // Then
        assertThat(foundUser.point()).isEqualTo(0);
        assertThat(product.stockQuantity()).isEqualTo(0);
    }

    @Test
    @DisplayName("재고 부족으로 인한 실패 시 재고 롤백 테스트")
    void when_not_enough_product_stock_then_roll_back_product_stock() {
        // Given
        Long userId = 1L;
        Long productId = 1L;
        Long productOrderQuantity = 1L;
        Long tooMuchProductOrderQuantity = 100L;

        OrderRequest request1 = new OrderRequest(
                new Receiver(
                        "홍길동",
                        "서울특별시 송파구",
                        "01012345678"
                ),
                List.of(
                        new OrderRequest.ProductOrderRequest(productId, productOrderQuantity)
                ),
                1000L,
                "CARD"
        );

        OrderRequest request2 = new OrderRequest(
                new Receiver(
                        "홍길동",
                        "서울특별시 송파구",
                        "01012345678"
                ),
                List.of(
                        new OrderRequest.ProductOrderRequest(productId, tooMuchProductOrderQuantity)
                ),
                1000L,
                "CARD"
        );

        // When
        orderUseCase.order(userId, request1);

        Product succeedDecreaseStockProduct = productService.getProductDetail(productId);
        assertThat(succeedDecreaseStockProduct.stockQuantity()).isEqualTo(5L - 1L);

        User succeedDecreasePointUser = userService.getUser(userId);
        assertThat(succeedDecreasePointUser.point()).isEqualTo(5000L - 1000L);

        // Then
        assertThrows(IllegalArgumentException.class, () -> {
            orderUseCase.order(userId, request2);
        });

        Product failedDecreaseStockProduct = productService.getProductDetail(productId);
        assertThat(failedDecreaseStockProduct.stockQuantity()).isEqualTo(4L);
    }

    @Test
    @DisplayName("잔액 부족으로 인한 실패 시 재고 및 잔액 롤백 테스트")
    void when_not_enough_user_point_then_roll_back_user_point() {
        // Given
        Long userId = 1L;
        Long productId = 1L;
        Long productOrderQuantity = 1L;
        Long paymentAmount = 999_999L;

        OrderRequest request = new OrderRequest(
                new Receiver(
                        "홍길동",
                        "서울특별시 송파구",
                        "01012345678"
                ),
                List.of(
                        new OrderRequest.ProductOrderRequest(productId, productOrderQuantity)
                ),
                paymentAmount,
                "CARD"
        );

        // When && Then
        assertThrows(IllegalArgumentException.class, () -> {
            orderUseCase.order(userId, request);
        });

        User user = userService.getUser(userId);
        assertThat(user.point()).isEqualTo(5000L);
    }
}