package com.hanghae.ecommerce.domain.payment;

import java.time.LocalDateTime;

public record Payment(
        Long id,
        Long orderId,
        Long payAmount,
        String paymentMethod,
        LocalDateTime paidAt
) {
}
