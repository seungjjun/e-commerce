package com.hanghae.ecommerce.domain.user;

import org.springframework.stereotype.Service;

import com.hanghae.ecommerce.common.LockHandler;

@Service
public class UserPointService {
	private final UserReader userReader;
	private final UserPointManager userPointManager;
	private final LockHandler lockHandler;

	private static final String USER_POINT_LOCK_PREFIX = "USER_";

	public UserPointService(UserReader userReader, UserPointManager userPointManager, LockHandler lockHandler) {
		this.userReader = userReader;
		this.userPointManager = userPointManager;
		this.lockHandler = lockHandler;
	}

	public Long chargePoint(Long userId, Long amount) {
		String key = USER_POINT_LOCK_PREFIX + userId;
		lockHandler.lock(key, 2, 1);
		User chargedUser;
		try {
			chargedUser = userPointManager.chargePoint(userId, amount);
		} finally {
			lockHandler.unlock(key);
		}
		return chargedUser.point();
	}

	public Long getPoint(Long userId) {
		User user = userReader.readById(userId);
		return user.point();
	}
}
