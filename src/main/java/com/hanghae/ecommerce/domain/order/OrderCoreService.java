package com.hanghae.ecommerce.domain.order;

import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.domain.product.Product;
import com.hanghae.ecommerce.domain.user.User;

import java.util.List;

public interface OrderCoreService {
    Order order(User userId, List<Product> products, OrderRequest request);
}
