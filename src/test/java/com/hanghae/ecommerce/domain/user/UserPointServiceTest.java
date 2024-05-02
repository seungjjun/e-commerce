package com.hanghae.ecommerce.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.common.LockHandler;

class UserPointServiceTest {
	private UserPointService userPointService;
	private UserReader userReader;
	private UserPointManager userPointManager;
	private LockHandler lockHandler;

	@BeforeEach
	void setUp() {
		userReader = mock(UserReader.class);
		userPointManager = mock(UserPointManager.class);
		lockHandler = mock(LockHandler.class);

		userPointService = new UserPointService(userReader, userPointManager, lockHandler);
	}

	@Test
	@DisplayName("포인트 충전 성공")
	void when_charging_point_is_positive_then_succeed_charge_point() {
		// Given
		Long userId = 1L;
		Long chargingPoint = 500L;
		User user = Fixtures.user(userId);

		given(userReader.readById(userId)).willReturn(user);
		given(userPointManager.chargePoint(userId, chargingPoint))
			.willReturn(
				new User(user.id(), user.name(), user.address(), user.phoneNumber(), user.point() + chargingPoint));

		// When
		Long balance = userPointService.chargePoint(userId, chargingPoint);

		// Then
		assertThat(balance).isEqualTo(50_000L + 500L);
	}

	@Test
	@DisplayName("잔액 조회 성공")
	void get_user_point_balance() {
		// Given
		Long userId = 1L;

		given(userReader.readById(userId)).willReturn(Fixtures.user(userId));

		// When
		Long balance = userPointService.getPoint(userId);

		// Then
		assertThat(balance).isEqualTo(50_000L);
	}
}
