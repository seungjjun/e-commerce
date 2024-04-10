package com.hanghae.ecommerce.domain.product;

import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.storage.order.OrderStatus;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ProductReader {
    private final ProductRepository productRepository;

    public ProductReader(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> readAll() {
        return productRepository.findAll();
    }

    public Product readById(Long productId) {
        return productRepository.findById(productId);
    }

    public List<Product> readAllByIds(List<OrderRequest.ProductOrderRequest> products) {
        return productRepository.findByIdIn(products.stream()
                .map(OrderRequest.ProductOrderRequest::id)
                .toList()
        );
    }

    public List<Product> readPopularProducts() {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(3);
        Pageable topFive = PageRequest.of(0, 5);

        return productRepository.findTopSellingProducts(OrderStatus.PAID, startDate, endDate, topFive);
    }
}
