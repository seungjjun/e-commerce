package com.hanghae.ecommerce.api.dto.response;

import com.hanghae.ecommerce.api.dto.OrderPaidResult;
import com.hanghae.ecommerce.api.dto.request.Receiver;
import java.time.format.DateTimeFormatter;

public record OrderResponse(
        Long orderId,
        Long paymentId,
        Long payAmount,
        Receiver receiver,
        String paymentMethod,
        String orderedAt,
        String paidAt
) {
    private final static DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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
