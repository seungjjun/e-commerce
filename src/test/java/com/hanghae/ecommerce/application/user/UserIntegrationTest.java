package com.hanghae.ecommerce.application.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.hanghae.ecommerce.domain.user.User;
import com.hanghae.ecommerce.domain.user.UserPointService;
import com.hanghae.ecommerce.domain.user.UserService;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserIntegrationTest {

	@Autowired
	private UserPointService userPointService;

	@Autowired
	private UserService userService;

	@Test
	@DisplayName("동시에 100건의 사용자 포인트 충전")
	void concurrency_charge_point() throws InterruptedException {
		// Given
		int numThreads = 100;
		ExecutorService executor = Executors.newFixedThreadPool(numThreads);
		CountDownLatch latch = new CountDownLatch(numThreads);

		Long userId = 1L;
		Long amount = 50L;

		// When

		for (int i = 0; i < numThreads; i += 1) {
			executor.submit(() -> {
				try {
					userPointService.chargePoint(1L, amount);
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();
		executor.shutdown();

		// Then
		User user = userService.getUser(userId);

		assertThat(user.point()).isEqualTo(100_000L + (50 * 100));
	}
}
