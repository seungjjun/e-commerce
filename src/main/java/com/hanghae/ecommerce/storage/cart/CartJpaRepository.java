package com.hanghae.ecommerce.storage.cart;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartJpaRepository extends JpaRepository<CartEntity, Long> {
	Optional<CartEntity> findByUserId(Long userId);
}
