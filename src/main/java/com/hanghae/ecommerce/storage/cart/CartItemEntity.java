package com.hanghae.ecommerce.storage.cart;

import com.hanghae.ecommerce.domain.cart.CartItem;
import com.hanghae.ecommerce.storage.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItemEntity extends BaseEntity {
	@Column(name = "cart_id")
	private Long cartId;

	@Column(name = "product_id")
	private Long productId;

	@Column(name = "quantity")
	private Long quantity;

	private Boolean deleted = false;

	public CartItemEntity(Long cartId, Long productId, Long quantity) {
		this.cartId = cartId;
		this.productId = productId;
		this.quantity = quantity;
	}

	public void addQuantity(Long addedQuantity) {
		this.quantity += addedQuantity;
	}

	public void delete() {
		this.deleted = true;
	}

	public CartItem toCartItem() {
		return new CartItem(getId(), productId, quantity);
	}

	public boolean isDeleted() {
		return deleted;
	}
}
