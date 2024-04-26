package com.hanghae.ecommerce;

import java.time.LocalDateTime;

import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.orderitem.OrderItem;
import com.hanghae.ecommerce.domain.payment.Payment;
import com.hanghae.ecommerce.domain.product.Product;
import com.hanghae.ecommerce.domain.product.Stock;
import com.hanghae.ecommerce.domain.user.User;
import com.hanghae.ecommerce.storage.order.OrderStatus;
import com.hanghae.ecommerce.storage.payment.PayType;

import jakarta.persistence.EntityNotFoundException;

public class Fixtures {
	public static Product product(String name) {
		if (name.equals("후드티")) {
			return new Product(1L, "후드티", 50_000L, "그레이 후드티", 5L);
		}

		if (name.equals("맨투맨")) {
			return new Product(2L, "맨투맨", 39_000L, "늘어나지 않는 맨투맨", 10L);
		}

		if (name.equals("슬랙스")) {
			return new Product(3L, "슬랙스", 20_000L, "와이드 슬랙스", 100L);
		}

		if (name.equals("백팩")) {
			return new Product(4L, "백팩", 60_000L, "아주 큰 백팩", 5L);
		}

		if (name.equals("모자")) {
			return new Product(5L, "모자", 15_000L, "캡 모자", 50L);
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
			return new Order(1L, 1L, 89_000L, "홍길동", "서울특별시 송파구", "01012345678", OrderStatus.READY.toString(),
				LocalDateTime.now());
		}

		if (orderStatus.equals(OrderStatus.COMPLETE)) {
			return new Order(1L, 1L, 89_000L, "홍길동", "서울특별시 송파구", "01012345678", OrderStatus.CANCELED.toString(),
				LocalDateTime.now());
		}

		if (orderStatus.equals(OrderStatus.CANCELED)) {
			return new Order(1L, 1L, 89_000L, "홍길동", "서울특별시 송파구", "01012345678", OrderStatus.CANCELED.toString(),
				LocalDateTime.now());
		}

		if (orderStatus.equals(OrderStatus.PAID)) {
			return new Order(1L, 1L, 89_000L, "홍길동", "서울특별시 송파구", "01012345678", OrderStatus.PAID.toString(),
				LocalDateTime.now());
		}

		if (orderStatus.equals(OrderStatus.PAY_FAILED)) {
			return new Order(1L, 1L, 89_000L, "홍길동", "서울특별시 송파구", "01012345678", OrderStatus.PAY_FAILED.toString(),
				LocalDateTime.now());
		}

		if (orderStatus.equals(OrderStatus.WAITING_FOR_PAY)) {
			return new Order(1L, 1L, 89_000L, "홍길동", "서울특별시 송파구", "01012345678", OrderStatus.WAITING_FOR_PAY.toString(),
				LocalDateTime.now());
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

	public static Payment payment(Long orderId) {
		if (orderId.equals(1L)) {
			return new Payment(1L, orderId, 89_000L, PayType.CARD.toString(), LocalDateTime.now());
		}

		throw new EntityNotFoundException("Payment Not Found - order id: " + orderId);
	}

	public static Stock stock(Long productId) {
		if (productId.equals(1L)) {
			return new Stock(1L, 1L, 5L);
		}

		if (productId.equals(2L)) {
			return new Stock(2L, 2L, 10L);
		}
		throw new EntityNotFoundException("Product Stock Not Found - stock id: " + productId);
	}
}
