package com.hanghae.ecommerce.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import com.hanghae.ecommerce.domain.user.User;
import com.hanghae.ecommerce.domain.user.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Repository
@Slf4j
public class UserCoreRepository implements UserRepository {
	private final UserJpaRepository userJpaRepository;

	public UserCoreRepository(UserJpaRepository userJpaRepository) {
		this.userJpaRepository = userJpaRepository;
	}

	@Override
	public User findById(Long userId) {
		return userJpaRepository.findById(userId)
			.orElseThrow(() -> new EntityNotFoundException("사용자를 찾지 못했습니다. - id: " + userId))
			.toUser();
	}

	@Override
	public User updateUserPoint(User user) {
		UserEntity userEntity = userJpaRepository.findById(user.id())
			.orElseThrow(() -> new EntityNotFoundException("사용자를 찾지 못했습니다. - id: " + user.id()));
		userEntity.updatePoint(user);
		return userJpaRepository.save(userEntity).toUser();
	}

	@Override
	public User findByIdWithLock(Long userId) {
		return userJpaRepository.findByIdWithLock(userId)
			.orElseThrow(() -> new EntityNotFoundException("사용자를 찾지 못했습니다. - id: " + userId))
			.toUser();
	}
}
