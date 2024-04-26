package com.hanghae.ecommerce.storage.user;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityNotFoundException;

class UserCoreRepositoryTest {
	private UserCoreRepository userCoreRepository;

	private UserJpaRepository userJpaRepository;

	@BeforeEach
	void setUp() {
		userJpaRepository = mock(UserJpaRepository.class);

		userCoreRepository = new UserCoreRepository(userJpaRepository);
	}

	@Test
	@DisplayName("사용자 정보를 못 찾을 경우 에러가 발생한다.")
	void when_not_found_user_then_error() {
		// Given
		Long userId = 999L;

		// When && Then
		assertThrows(EntityNotFoundException.class, () -> {
			userCoreRepository.findById(userId);
		});
	}
}
