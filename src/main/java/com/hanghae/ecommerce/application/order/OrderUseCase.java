package com.hanghae.ecommerce.application.order;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hanghae.ecommerce.api.dto.OrderPaidResult;
import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.domain.cart.Cart;
import com.hanghae.ecommerce.domain.cart.CartService;
import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.order.OrderItem;
import com.hanghae.ecommerce.domain.order.OrderService;
import com.hanghae.ecommerce.domain.order.event.OrderCreatedEvent;
import com.hanghae.ecommerce.domain.payment.Payment;
import com.hanghae.ecommerce.domain.payment.PaymentService;
import com.hanghae.ecommerce.domain.product.StockService;
import com.hanghae.ecommerce.domain.user.User;
import com.hanghae.ecommerce.domain.user.UserService;
import com.hanghae.ecommerce.storage.order.OrderItemStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderUseCase {

	private final UserService userService;
	private final CartService cartService;
	private final StockService stockService;
	private final OrderService orderService;
	private final PaymentService paymentService;
	private final ApplicationEventPublisher applicationEventPublisher;

	@Transactional
	public OrderPaidResult order(Long userId, OrderRequest request) {
		User user = userService.getUser(userId);
		Cart cart = cartService.getCart(user);
		Order order = orderService.order(user, cart, request);

		try {
			for (OrderItem item : order.items()) {
				stockService.decreaseProductStock(item);
				orderService.updateItemStatus(item, OrderItemStatus.SUCCESS);
			}
			cartService.resetCart(user);
			Payment payment = paymentService.pay(user, order, request);

			applicationEventPublisher.publishEvent(new OrderCreatedEvent(order, payment));
			return OrderPaidResult.of(order, payment);
		} catch (Exception e) {
			orderService.orderFailed(order);
			stockService.compensateOrderStock(order);
			throw new RuntimeException(e.getMessage());
		}
	}
}
