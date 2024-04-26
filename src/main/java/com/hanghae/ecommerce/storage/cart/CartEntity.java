package com.hanghae.ecommerce.storage.cart;

import com.hanghae.ecommerce.domain.cart.Cart;
import com.hanghae.ecommerce.storage.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "carts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartEntity extends BaseEntity {
	private Long userId;

	public CartEntity(Long userId) {
		this.userId = userId;
	}

	public Cart toCart() {
		return new Cart(getId(), userId);
	}
}
