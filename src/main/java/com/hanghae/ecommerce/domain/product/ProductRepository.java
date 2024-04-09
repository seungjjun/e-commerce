package com.hanghae.ecommerce.domain.product;

import java.util.List;

public interface ProductRepository {
    List<Product> findAll();

    Product findById(Long productId);

    List<Product> findByIdIn(List<Long> productIds);

    void updateStock(Product product);
}
