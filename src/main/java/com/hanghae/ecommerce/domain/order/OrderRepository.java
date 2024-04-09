package com.hanghae.ecommerce.domain.order;

import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.domain.user.User;
import com.hanghae.ecommerce.storage.order.OrderStatus;

public interface OrderRepository {
    Order order(User user, OrderRequest request);

    Order updateStatus(Order order, OrderStatus orderStatus);

    Order findById(Long orderId);
}
