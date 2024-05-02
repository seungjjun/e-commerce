package com.hanghae.ecommerce.domain.user;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
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
	public User usePoint(User user, Long payAmount) {
		User foundUser = userReader.readById(user.id());
		foundUser.isEnoughPointForPay(payAmount);
		return userRepository.updateUserPoint(foundUser.minusPoint(payAmount));
	}
}
