package com.hanghae.ecommerce.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.hanghae.ecommerce.Fixtures;

class UserTest {

	private User user = Fixtures.user(1L);

	@Test
	@DisplayName("포인트 충전 테스트")
	void addPoint() {
		// When
		User addedPointUser = user.addPoint(10_000L);

		// Then
		assertThat(addedPointUser.point()).isEqualTo(50_000 + 10_000);
	}

	@Test
	@DisplayName("포인트 차감 테스트")
	void minusPoint() {
		// When
		User minusedPointUser = user.minusPoint(30_000L);

		// Then
		assertThat(minusedPointUser.point()).isEqualTo(50_000 - 30_000);
	}

	@Test
	@DisplayName("결제 시 포인트가 충분한 경우 에러가 발생하지 않는다.")
	void when_enough_point_for_pay_then_not_throw_exception() {
		// Given
		Long payAmount = 500L;

		// When && Then
		assertDoesNotThrow(() -> {
			user.isEnoughPointForPay(payAmount);
		});
	}

	@Test
	@DisplayName("결제 시 포인트가 부족한 경우 에러가 발생한다.")
	void when_not_enough_point_for_pay_then_throw_exception() {
		// Given
		Long payAmount = 999_999L;

		// When && Then
		assertThrows(IllegalArgumentException.class, () -> {
			user.isEnoughPointForPay(payAmount);
		});
	}
}
