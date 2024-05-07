package com.hanghae.ecommerce.domain.cart;

import com.hanghae.ecommerce.domain.user.User;

public interface CartRepository {
	Cart findByUserId(Long userId);

	void resetCart(User user);
}
