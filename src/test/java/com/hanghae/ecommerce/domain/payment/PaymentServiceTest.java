package com.hanghae.ecommerce.domain.payment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.api.dto.request.Receiver;
import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.order.OrderUpdater;
import com.hanghae.ecommerce.domain.user.User;
import com.hanghae.ecommerce.domain.user.UserPointManager;
import com.hanghae.ecommerce.storage.order.OrderStatus;
import com.hanghae.ecommerce.storage.payment.PayType;

class PaymentServiceTest {

	private PaymentService paymentService;
	private OrderUpdater orderUpdater;
	private PaymentAppender paymentAppender;
	private UserPointManager userPointManager;

	private Long userId;
	private Long orderId;
	private Long payAmount;
	private User user;
	private OrderRequest request;

	@BeforeEach
	void setUp() {
		orderUpdater = mock(OrderUpdater.class);
		paymentAppender = mock(PaymentAppender.class);
		userPointManager = mock(UserPointManager.class);

		paymentService =
			new PaymentService(orderUpdater, paymentAppender, userPointManager);

		userId = 1L;
		orderId = 1L;
		payAmount = 50_000L;
		String paymentMethod = PayType.CARD.toString();

		user = Fixtures.user(userId);

		request = new OrderRequest(new Receiver(
			user.name(),
			user.address(),
			user.phoneNumber()),
			payAmount,
			paymentMethod
		);
	}

	@Test
	@DisplayName("카드 결제 성공")
	void succeed_pay_with_card() {
		// Given
		Payment payment = Fixtures.payment(orderId);
		Order order = Fixtures.order(OrderStatus.READY);

		given(paymentAppender.create(any(), any(), any())).willReturn(payment);

		// When
		Payment paid = paymentService.pay(user, order, request);

		// Then
		assertThat(paid).isNotNull();
		assertThat(paid.payAmount()).isEqualTo(89_000L);
		assertThat(paid.paymentMethod()).isEqualTo("CARD");

		verify(userPointManager, atLeastOnce()).usePoint(any(), any());
		verify(orderUpdater, atLeastOnce()).changeStatus(any(), any());
	}
}
