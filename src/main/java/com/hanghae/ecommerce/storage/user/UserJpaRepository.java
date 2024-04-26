package com.hanghae.ecommerce.storage.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import jakarta.persistence.LockModeType;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Optional<UserEntity> findById(Long userId);
}
