package com.hanghae.ecommerce.domain.payment;

import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.user.User;

public interface PaymentCoreService {
    Payment pay(User user, Order order, OrderRequest request);
}
