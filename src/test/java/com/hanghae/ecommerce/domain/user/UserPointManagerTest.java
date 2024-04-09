package com.hanghae.ecommerce.domain.user;

import com.hanghae.ecommerce.Fixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class UserPointManagerTest {
    private UserPointManager userPointManager;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);

        userPointManager = new UserPointManager(userRepository);
    }

    @Test
    @DisplayName("사용자 포인트를 충전한다.")
    void charge_point() {
        // Given
        User user = Fixtures.user(1L);
        Long chargingPoint = 20_000L;

        assertThat(user.point()).isEqualTo(50_000L);

        given(userRepository.updateUserPoint(any())).willReturn(user.addPoint(chargingPoint));

        // When
        User chargedUser = userPointManager.chargePoint(user, chargingPoint);

        // Then
        assertThat(chargedUser.point()).isEqualTo(50_000L + 20_000L);
    }

    @Test
    @DisplayName("사용자 포인트 차감 성공")
    void when_enough_point_then_succeed_use_point() {
        // Given
        User user = Fixtures.user(1L);
        Long payAmount = 10_000L;

        given(userRepository.updateUserPoint(any())).willReturn(user.minusPoint(payAmount));

        // When
        User usedPoint = userPointManager.usePoint(user, payAmount);

        // Then
        assertThat(usedPoint.point()).isEqualTo(50_000L - 10_000L);
    }
}
