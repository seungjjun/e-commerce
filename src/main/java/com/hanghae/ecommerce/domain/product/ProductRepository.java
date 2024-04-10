package com.hanghae.ecommerce.domain.product;

import com.hanghae.ecommerce.storage.order.OrderStatus;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductRepository {
    List<Product> findAll();

    Product findById(Long productId);

    List<Product> findByIdIn(List<Long> productIds);

    void updateStock(Product product);

    List<Product> findTopSellingProducts(OrderStatus orderStatus,
                                         LocalDateTime startDate,
                                         LocalDateTime endDate,
                                         Pageable pageable);
}
