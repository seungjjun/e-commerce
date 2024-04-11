package com.hanghae.ecommerce.domain.order;

import com.hanghae.ecommerce.storage.order.OrderStatus;

import java.time.LocalDateTime;

public record Order(
        Long id,
        Long userId,
        Long payAmount,
        String receiverName,
        String address,
        String phoneNumber,
        String orderStatus,
        LocalDateTime orderedAt
) {
    public Order changeStatus(OrderStatus orderStatus) {
        return new Order(id, userId, payAmount, receiverName, address, phoneNumber, orderStatus.toString(), orderedAt);
    }
}
