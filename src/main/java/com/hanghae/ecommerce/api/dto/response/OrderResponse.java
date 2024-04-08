package com.hanghae.ecommerce.api.dto.response;

import com.hanghae.ecommerce.api.dto.request.Receiver;
import com.hanghae.ecommerce.domain.order.Order;

import java.time.format.DateTimeFormatter;

public record OrderResponse(
        Long orderId,
        Receiver receiver,
        Long payAmount,
        String status,
        String orderedAt
) {
    private final static DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static OrderResponse from(Order order) {
        return new OrderResponse(
                order.id(),
                new Receiver(
                        order.receiverName(),
                        order.address(),
                        order.phoneNumber()
                ),
                order.payAmount(),
                order.orderStatus(),
                order.orderedAt().format(DATE_TIME_FORMATTER)
        );
    }
}
