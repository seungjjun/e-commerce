package com.hanghae.ecommerce.storage.cart;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemJpaRepository extends JpaRepository<CartItemEntity, Long> {
	List<CartItemEntity> findByIdIn(List<Long> ids);

	List<CartItemEntity> findAllByCartId(Long cartId);

	Optional<CartItemEntity> findByCartIdAndProductIdAndDeletedFalse(Long cartId, Long productId);
}
