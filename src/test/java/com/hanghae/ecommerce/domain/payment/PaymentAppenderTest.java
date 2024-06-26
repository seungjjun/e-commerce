package com.hanghae.ecommerce.domain.payment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.storage.order.OrderStatus;
import com.hanghae.ecommerce.storage.payment.PayType;

class PaymentAppenderTest {
	private PaymentAppender paymentAppender;

	private PaymentRepository paymentRepository;

	@BeforeEach
	void setUp() {
		paymentRepository = mock(PaymentRepository.class);

		paymentAppender = new PaymentAppender(paymentRepository);
	}

	@Test
	@DisplayName("결제 생성 성공")
	void succeed_create_payment() {
		// Given
		Order order = Fixtures.order(OrderStatus.READY);
		Payment payment = Fixtures.payment(order.id());

		given(paymentRepository.create(any(), any(), any())).willReturn(payment);

		// When
		Payment paid = paymentAppender.create(order, order.payAmount(), PayType.CARD.toString());

		// Then
		assertThat(paid).isNotNull();
		assertThat(paid.payAmount()).isEqualTo(89_000L);
		assertThat(paid.paymentMethod()).isEqualTo("CARD");
	}

}
