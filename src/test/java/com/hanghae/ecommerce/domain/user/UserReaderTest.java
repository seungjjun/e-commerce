package com.hanghae.ecommerce.domain.user;

import com.hanghae.ecommerce.Fixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class UserReaderTest {
    private UserReader userReader;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);

        userReader = new UserReader(userRepository);
    }

    @Test
    @DisplayName("사용자를 조회한다.")
    void readUser() {
        // Given
        Long userId = 1L;
        User user = Fixtures.user(userId);

        given(userRepository.findById(userId)).willReturn(user);

        // When
        User foundUser = userReader.readById(userId);

        // Then
        assertThat(foundUser.name()).isEqualTo("홍길동");
        assertThat(foundUser.point()).isEqualTo(50_000L);
    }
}
