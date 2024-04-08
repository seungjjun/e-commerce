package com.hanghae.ecommerce.storage.order;

import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.api.dto.request.Receiver;
import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.order.OrderRepository;
import com.hanghae.ecommerce.domain.user.User;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class OrderCoreRepository implements OrderRepository {
    private final OrderJpaRepository orderJpaRepository;

    public OrderCoreRepository(OrderJpaRepository orderJpaRepository) {
        this.orderJpaRepository = orderJpaRepository;
    }

    @Override
    public Order order(User user, OrderRequest request) {
        Receiver receiver = request.receiver();
        OrderEntity orderEntity = new OrderEntity(
                user.id(),
                request.paymentAmount(),
                receiver.name(),
                receiver.address(),
                receiver.phoneNumber(),
                OrderStatus.READY,
                LocalDateTime.now()
        );
        return orderJpaRepository.save(orderEntity).toOrder();
    }

    @Override
    public Order updateStatus(Order order, OrderStatus orderStatus) {
        OrderEntity orderEntity = orderJpaRepository.findById(order.id())
                .orElseThrow();
        orderEntity.updateStatus(orderStatus);
        return orderJpaRepository.save(orderEntity).toOrder();
    }
}
