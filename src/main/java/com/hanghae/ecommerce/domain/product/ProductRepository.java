package com.hanghae.ecommerce.domain.product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.hanghae.ecommerce.storage.order.OrderStatus;
import com.hanghae.ecommerce.storage.product.ProductEntity;

public interface ProductRepository {
	List<Product> findAll();

	Product findById(Long productId);

	List<Product> findByIdIn(List<Long> productIds);

	Product updateStock(Product product);

	List<Product> findTopSellingProducts(
		OrderStatus orderStatus,
		LocalDateTime startDate,
		LocalDateTime endDate,
		Pageable pageable);
}
