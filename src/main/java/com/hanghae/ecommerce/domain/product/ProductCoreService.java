package com.hanghae.ecommerce.domain.product;

import com.hanghae.ecommerce.api.dto.request.OrderRequest;

import java.util.List;

public interface ProductCoreService {
    List<Product> getProducts();

    Product getProductDetail(Long productId);

    List<Product> getPopularProducts();

    List<Product> getProductsByIds(List<OrderRequest.ProductOrderRequest> products);

    List<Product> decreaseStock(List<Product> products, OrderRequest orderRequests);
}
