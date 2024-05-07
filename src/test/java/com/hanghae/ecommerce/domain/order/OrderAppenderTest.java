package com.hanghae.ecommerce.domain.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.api.dto.request.Receiver;
import com.hanghae.ecommerce.domain.cart.Cart;
import com.hanghae.ecommerce.domain.cart.CartItem;
import com.hanghae.ecommerce.domain.product.Product;
import com.hanghae.ecommerce.domain.user.User;
import com.hanghae.ecommerce.storage.order.OrderItemStatus;
import com.hanghae.ecommerce.storage.order.OrderStatus;

class OrderAppenderTest {
	private OrderAppender orderAppender;

	private OrderRepository orderRepository;
	private OrderProductReader orderProductReader;

	@BeforeEach
	void setUp() {
		orderRepository = mock(OrderRepository.class);
		orderProductReader = mock(OrderProductReader.class);

		orderAppender = new OrderAppender(orderRepository, orderProductReader);
	}

	@Test
	@DisplayName("주문 생성")
	void createOrder() {
		// Given
		User user = Fixtures.user(1L);
		Product product1 = Fixtures.product("후드티");
		Product product2 = Fixtures.product("맨투맨");

		Cart cart = new Cart(1L, user.id(), List.of(
			new CartItem(1L, product1.id(), 5L),
			new CartItem(1L, product2.id(), 3L)
		));

		List<OrderProduct> orderProducts = List.of(
			new OrderProduct(product1.id(), product1.name(), product1.price(), product1.orderTotalPrice(5L), 5L),
			new OrderProduct(product2.id(), product2.name(), product2.price(), product2.orderTotalPrice(3L), 3L));

		OrderRequest orderRequest = new OrderRequest(
			new Receiver(
				user.name(),
				user.address(),
				user.phoneNumber()
			),
			50_000L,
			"CARD"
		);

		Order order = new Order(
			1L,
			user.id(),
			orderRequest.paymentAmount(),
			List.of(
				new OrderItem(1L, 1L, product1.id(), product1.name(), product1.price(), product1.orderTotalPrice(5L),
					5L, OrderItemStatus.CREATED.toString()),
				new OrderItem(2L, 1L, product2.id(), product2.name(), product2.price(), product2.orderTotalPrice(3L),
					3L, OrderItemStatus.CREATED.toString())
			),
			orderRequest.receiver().name(),
			orderRequest.receiver().address(),
			orderRequest.receiver().phoneNumber(),
			OrderStatus.READY.toString(),
			LocalDateTime.now());


		given(orderProductReader.read(any())).willReturn(orderProducts);
		given(orderRepository.create(any(), any())).willReturn(order);

		// When
		Order createdOrder = orderAppender.append(user, cart, orderRequest);

		// Then
		assertThat(createdOrder.orderStatus()).isEqualTo("READY");
		assertThat(createdOrder.payAmount()).isEqualTo(50_000L);
	}
}
