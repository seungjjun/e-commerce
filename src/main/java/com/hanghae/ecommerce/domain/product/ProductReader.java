package com.hanghae.ecommerce.domain.product;

import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import org.springframework.stereotype.Component;

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
}
