package com.hanghae.ecommerce.domain.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Component
@Slf4j
public class UserPointManager {
	private final UserReader userReader;
	private final UserRepository userRepository;

	public UserPointManager(UserReader userReader, UserRepository userRepository) {
		this.userReader = userReader;
		this.userRepository = userRepository;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public User chargePoint(Long userId, Long chargingPoint) {
		User user = userReader.readById(userId);
		return userRepository.updateUserPoint(user.addPoint(chargingPoint));
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public User usePoint(Long userId, Long payAmount) {
		log.info("use Thread: {}", Thread.currentThread());
		log.info("use Transaction: {}", TransactionSynchronizationManager.getCurrentTransactionName());
		User foundUser = userReader.readByIdWithLock(userId);
		foundUser.isEnoughPointForPay(payAmount);
		return userRepository.updateUserPoint(foundUser.minusPoint(payAmount));
	}
}
