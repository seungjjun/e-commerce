package com.hanghae.ecommerce.domain.payment;

import com.hanghae.ecommerce.api.dto.request.PaymentRequest;

public interface PaymentCoreService {
    Payment pay(Long userId, PaymentRequest request);
}
