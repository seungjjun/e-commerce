package com.hanghae.ecommerce.domain.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

	private final UserReader userReader;

	public UserService(UserReader userReader) {
		this.userReader = userReader;
	}

	@Transactional(readOnly = true)
	public User getUser(Long userId) {
		return userReader.readById(userId);
	}
}
