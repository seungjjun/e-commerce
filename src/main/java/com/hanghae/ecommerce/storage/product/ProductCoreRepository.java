package com.hanghae.ecommerce.storage.product;

import com.hanghae.ecommerce.domain.product.Product;
import com.hanghae.ecommerce.domain.product.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;

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
}
