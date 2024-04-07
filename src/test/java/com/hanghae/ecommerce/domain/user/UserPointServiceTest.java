package com.hanghae.ecommerce.domain.user;

import com.hanghae.ecommerce.Fixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class UserPointServiceTest {
    private UserPointService userPointService;
    private UserReader userReader;
    private UserPointManager userPointManager;

    @BeforeEach
    void setUp() {
        userReader = mock(UserReader.class);
        userPointManager = mock(UserPointManager.class);

        userPointService = new UserPointService(userReader, userPointManager);
    }

    @Test
    @DisplayName("포인트 충전 성공")
    void when_charging_point_is_positive_then_succeed_charge_point() {
        // Given
        Long userId = 1L;
        Long chargingPoint = 500L;
        User user = Fixtures.user(userId);

        given(userReader.readById(userId)).willReturn(user);
        given(userPointManager.chargePoint(user, chargingPoint))
                .willReturn(new User(user.id(), user.name(), user.address(), user.phoneNumber(), user.point() + chargingPoint));

        // When
        Long balance = userPointService.chargePoint(userId, chargingPoint);

        // Then
        assertThat(balance).isEqualTo(50_000L + 500L);
    }
}
