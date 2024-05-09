package com.hanghae.ecommerce.storage.product;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.hanghae.ecommerce.domain.product.Product;
import com.hanghae.ecommerce.domain.product.ProductRepository;
import com.hanghae.ecommerce.storage.order.OrderStatus;

import jakarta.persistence.EntityNotFoundException;

@Repository
public class ProductCoreRepository implements ProductRepository {
	private final ProductJpaRepository productJpaRepository;

	public ProductCoreRepository(ProductJpaRepository productJpaRepository) {
		this.productJpaRepository = productJpaRepository;
	}

	@Override
	public List<Product> findAll() {
		return productJpaRepository.findAll().stream()
			.sorted(Comparator.comparing(ProductEntity::getCreatedAt).reversed())
			.map(ProductEntity::toProduct)
			.toList();
	}

	@Override
	public Optional<ProductEntity> findById(Long productId) {
		return productJpaRepository.findById(productId);
	}

	@Override
	public List<Product> findByIdIn(List<Long> productIds) {
		return productJpaRepository.findByIdIn(productIds).stream()
			.map(ProductEntity::toProduct)
			.toList();
	}

	@Override
	public Product updateStock(Product product) {
		ProductEntity productEntity = productJpaRepository.findById(product.id())
			.orElseThrow(() -> new EntityNotFoundException("상품 정보를 찾지 못했습니다. - id: " + product.id()));
		productEntity.updateStock(product.stockQuantity());
		return productEntity.toProduct();
	}

	@Override
	public List<Product> findTopSellingProducts(OrderStatus orderStatus, LocalDateTime startDate, LocalDateTime endDate,
												Pageable pageable) {
		return productJpaRepository.findPopularSellingProducts(orderStatus, startDate, endDate, pageable)
			.map(ProductEntity::toProduct)
			.toList();
	}
}
