package com.hanghae.ecommerce.domain.cart;

import java.util.List;

public record Cart(
	Long id,
	Long userId,
	List<CartItem> items) {
}
