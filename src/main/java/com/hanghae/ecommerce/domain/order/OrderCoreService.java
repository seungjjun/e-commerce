package com.hanghae.ecommerce.domain.order;

import com.hanghae.ecommerce.api.dto.request.OrderRequest;

public interface OrderCoreService {
    Order order(Long userId, OrderRequest request);
}
