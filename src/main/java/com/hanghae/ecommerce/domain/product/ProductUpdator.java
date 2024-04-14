package com.hanghae.ecommerce.domain.product;

import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductUpdator {
    private final ProductRepository productRepository;

    public ProductUpdator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void updateStock(List<Product> products, List<OrderRequest.ProductOrderRequest> request) {
        for (OrderRequest.ProductOrderRequest orderRequest : request) {
            Product product = products.stream()
                    .filter(p -> p.id().equals(orderRequest.id()))
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException(orderRequest.id() + " 상품의 정보를 찾지 못했습니다."));

            Product decreasedProduct = product.decreaseStock(orderRequest.quantity());
            productRepository.updateStock(decreasedProduct);
        }
    }
}
