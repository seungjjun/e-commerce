package com.hanghae.ecommerce.domain.user;

import com.hanghae.ecommerce.Fixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class UserTest {
    @Test
    @DisplayName("포인트 Add 테스트")
    void addPoint() {
        // Given
        User user = Fixtures.user(1L);

        // When
        User addedPointUser = user.addPoint(10_000L);

        // Then
        assertThat(addedPointUser.point()).isEqualTo(50_000 + 10_000);
    }

}
