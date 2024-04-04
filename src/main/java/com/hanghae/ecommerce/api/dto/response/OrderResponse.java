package com.hanghae.ecommerce.api.dto.response;

import com.hanghae.ecommerce.api.dto.request.Receiver;

public record OrderResponse(
        Long orderId,
        Receiver receiver,
        Long totalPrice,
        String orderedAt,
        String status
) {
}
