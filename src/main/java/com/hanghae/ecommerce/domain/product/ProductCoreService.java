package com.hanghae.ecommerce.domain.product;

import java.util.List;

public interface ProductCoreService {
    List<Product> getProducts();

    Product getProductDetail(Long productId);

    List<Product> getPopularProducts();
}
