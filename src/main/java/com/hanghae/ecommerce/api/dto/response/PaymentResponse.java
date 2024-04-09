package com.hanghae.ecommerce.api.dto.response;

import com.hanghae.ecommerce.domain.payment.Payment;

import java.time.format.DateTimeFormatter;

public record PaymentResponse(
        Long id,
        Long payAmount,
        String paymentMethod,
        String paidAt
) {
    private final static DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static PaymentResponse from(Payment payment) {
        return new PaymentResponse(
                payment.id(),
                payment.payAmount(),
                payment.paymentMethod(),
                payment.paidAt().format(DATE_TIME_FORMATTER)
        );
    }
}
