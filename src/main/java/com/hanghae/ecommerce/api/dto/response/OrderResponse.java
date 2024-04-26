package com.hanghae.ecommerce.api.dto.response;

import static com.hanghae.ecommerce.api.dto.common.DateUtils.DATE_TIME_FORMATTER;

import com.hanghae.ecommerce.api.dto.OrderPaidResult;
import com.hanghae.ecommerce.api.dto.request.Receiver;

public record OrderResponse(
	Long orderId,
	Long paymentId,
	Long payAmount,
	Receiver receiver,
	String paymentMethod,
	String orderedAt,
	String paidAt
) {

	public static OrderResponse from(OrderPaidResult orderPaidResult) {
		return new OrderResponse(
			orderPaidResult.orderId(),
			orderPaidResult.paymentId(),
			orderPaidResult.payAmount(),
			orderPaidResult.receiver(),
			orderPaidResult.paymentMethod(),
			orderPaidResult.orderedAt().format(DATE_TIME_FORMATTER),
			orderPaidResult.paidAt().format(DATE_TIME_FORMATTER)
		);
	}
}
