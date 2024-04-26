package com.hanghae.ecommerce.domain.payment;

public interface PaymentRepository {
	Payment create(Long orderId, Long payAmount, String paymentMethod);
}
