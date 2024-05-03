package com.hanghae.ecommerce.application.order;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hanghae.ecommerce.api.dto.OrderPaidResult;
import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.common.LockHandler;
import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.order.OrderService;
import com.hanghae.ecommerce.domain.order.event.OrderCreatedEvent;
import com.hanghae.ecommerce.domain.payment.Payment;
import com.hanghae.ecommerce.domain.payment.PaymentService;
import com.hanghae.ecommerce.domain.product.Product;
import com.hanghae.ecommerce.domain.product.ProductService;
import com.hanghae.ecommerce.domain.product.StockService;
import com.hanghae.ecommerce.domain.user.User;
import com.hanghae.ecommerce.domain.user.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OrderUseCase {

	private final UserService userService;
	private final ProductService productService;
	private final StockService stockService;
	private final OrderService orderService;
	private final PaymentService paymentService;
	private final LockHandler lockHandler;
	private final ApplicationEventPublisher applicationEventPublisher;

	private static final String ORDER_LOCK_PREFIX = "ORDER_";

	public OrderUseCase(UserService userService,
						ProductService productService,
						StockService stockService,
						OrderService orderService,
						PaymentService paymentService,
						LockHandler lockHandler,
						ApplicationEventPublisher applicationEventPublisher) {
		this.userService = userService;
		this.productService = productService;
		this.stockService = stockService;
		this.orderService = orderService;
		this.paymentService = paymentService;
		this.lockHandler = lockHandler;
		this.applicationEventPublisher = applicationEventPublisher;
	}

	@Transactional
	public OrderPaidResult order(Long userId, OrderRequest request) {
		User user = userService.getUser(userId);
		List<Product> products = productService.getProductsByIds(request.products().stream()
			.map(OrderRequest.ProductOrderRequest::id)
			.toList()
		);

		String key = createLockKey(products);

		lockHandler.lock(key, 2, 1);
		try {
			stockService.decreaseProductStock(products, request);
		} finally {
			lockHandler.unlock(key);
		}

		Order order = orderService.order(user, products, request);
		Payment payment = paymentService.pay(user, order, request);

		applicationEventPublisher.publishEvent(new OrderCreatedEvent(products, request.products(), order, payment));
		return OrderPaidResult.of(order, payment);
	}

	private String createLockKey(List<Product> products) {
		StringBuilder key = new StringBuilder(ORDER_LOCK_PREFIX);
		for (Product product : products) {
			key.append(product.id());
		}
		return key.toString();
	}
}
