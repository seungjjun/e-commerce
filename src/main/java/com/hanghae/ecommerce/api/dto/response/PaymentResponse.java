package com.hanghae.ecommerce.api.dto.response;

import static com.hanghae.ecommerce.api.dto.common.DateUtils.DATE_TIME_FORMATTER;

import com.hanghae.ecommerce.domain.payment.Payment;

public record PaymentResponse(
	Long id,
	Long payAmount,
	String paymentMethod,
	String paidAt
) {

	public static PaymentResponse from(Payment payment) {
		return new PaymentResponse(
			payment.id(),
			payment.payAmount(),
			payment.paymentMethod(),
			payment.paidAt().format(DATE_TIME_FORMATTER)
		);
	}
}
