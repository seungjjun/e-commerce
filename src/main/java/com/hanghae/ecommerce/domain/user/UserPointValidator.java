package com.hanghae.ecommerce.domain.user;

import org.springframework.stereotype.Component;

@Component
public class UserPointValidator {

	public void checkUserPointForPay(User user, Long payAmount) {
		user.isEnoughPointForPay(payAmount);
	}
}
