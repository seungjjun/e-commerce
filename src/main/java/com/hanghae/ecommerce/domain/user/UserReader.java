package com.hanghae.ecommerce.domain.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Component
@Slf4j
public class UserReader {
	private final UserRepository userRepository;

	public UserReader(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User readById(Long userId) {
		return userRepository.findById(userId);
	}

	public User readByIdWithLock(Long userId) {
		log.info("read Thread: {}", Thread.currentThread());
		log.info("read Transaction: {}", TransactionSynchronizationManager.getCurrentTransactionName());
		return userRepository.findByIdWithLock(userId);
	}
}
