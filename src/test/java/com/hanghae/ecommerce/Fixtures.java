package com.hanghae.ecommerce;

import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.orderitem.OrderItem;
import com.hanghae.ecommerce.domain.product.Product;
import com.hanghae.ecommerce.domain.user.User;
import com.hanghae.ecommerce.storage.order.OrderStatus;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;

public class Fixtures {
    public static Product product(String name) {
        if (name.equals("후드티")) {
            return new Product(1L, "후드티", 50_000L, "그레이 후드티", 5L);
        }

        if (name.equals("맨투맨")) {
            return new Product(2L, "맨투맨", 39_000L, "늘어나지 않는 맨투맨", 10L);
        }

        throw new EntityNotFoundException("Product Not Found - name: " + name);
    }

    public static User user(Long id) {
        if (id.equals(1L)) {
            return new User(1L, "홍길동", "서울특별시 송파구", "01012345678", 50_000L);
        }

        throw new EntityNotFoundException("User Not Found - id: " + id);
    }

    public static Order order(OrderStatus orderStatus) {
        if (orderStatus.equals(OrderStatus.READY)) {
            return new Order(1L, 1L, 89_000L, "홍길동", "서울특별시 송파구", "01012345678", "ready", LocalDateTime.now());
        }

        if (orderStatus.equals(OrderStatus.COMPLETE)) {
            return new Order(1L, 1L, 89_000L, "홍길동", "서울특별시 송파구", "01012345678", "complete", LocalDateTime.now());
        }

        if (orderStatus.equals(OrderStatus.CANCELED)) {
            return new Order(1L, 1L, 89_000L, "홍길동", "서울특별시 송파구", "01012345678", "canceled", LocalDateTime.now());
        }

        if (orderStatus.equals(OrderStatus.PAID)) {
            return new Order(1L, 1L, 89_000L, "홍길동", "서울특별시 송파구", "01012345678", "paid", LocalDateTime.now());
        }

        throw new EntityNotFoundException("Order Not Found - order status: " + orderStatus);
    }

    public static OrderItem orderItem(Long orderId, Long productId) {
        if (orderId.equals(1L) && productId.equals(1L)) {
            return new OrderItem(1L, orderId, productId, "후드티", 50_000L, 150_000L, 3L);
        }

        if (orderId.equals(2L) && productId.equals(2L)) {
            return new OrderItem(2L, orderId, productId, "맨투맨", 39_000L, 78_000L, 2L);
        }

        throw new EntityNotFoundException("Order Item Not Found - order id: " + orderId + ", product id: " + productId);
    }
}
