package com.hanghae.ecommerce.domain.payment;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.api.dto.request.PaymentRequest;
import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.order.OrderReader;
import com.hanghae.ecommerce.domain.order.OrderUpdater;
import com.hanghae.ecommerce.domain.order.OrderValidator;
import com.hanghae.ecommerce.domain.user.User;
import com.hanghae.ecommerce.domain.user.UserPointManager;
import com.hanghae.ecommerce.domain.user.UserPointValidator;
import com.hanghae.ecommerce.domain.user.UserReader;
import com.hanghae.ecommerce.storage.order.OrderStatus;
import com.hanghae.ecommerce.storage.payment.PayType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class PaymentServiceTest {

    private PaymentService paymentService;
    private UserReader userReader;
    private OrderReader orderReader;
    private OrderUpdater orderUpdater;
    private OrderValidator orderValidator;
    private PaymentAppender paymentAppender;
    private UserPointManager userPointManager;
    private UserPointValidator userPointValidator;

    @BeforeEach
    void setUp() {
        userReader = mock(UserReader.class);
        orderReader = mock(OrderReader.class);
        orderUpdater = mock(OrderUpdater.class);
        orderValidator = mock(OrderValidator.class);
        paymentAppender = mock(PaymentAppender.class);
        userPointManager = mock(UserPointManager.class);
        userPointValidator = mock(UserPointValidator.class);

        paymentService = new PaymentService(userReader, orderReader, orderUpdater, orderValidator, paymentAppender, userPointManager, userPointValidator);
    }

    @Test
    @DisplayName("카드 결제 성공")
    void succeed_pay_with_card() {
        // Given
        Long userId = 1L;
        Long orderId = 1L;
        Long payAmount = 50_000L;
        String paymentMethod = PayType.CARD.toString();
        PaymentRequest request = new PaymentRequest(orderId, payAmount, paymentMethod);

        User user = Fixtures.user(userId);
        Payment payment = Fixtures.payment(orderId);
        Order order = Fixtures.order(OrderStatus.COMPLETE);

        given(userReader.readById(userId)).willReturn(user);
        given(orderReader.readById(any())).willReturn(order);
        given(paymentAppender.create(any(), any(), any())).willReturn(payment);

        // When
        Payment paid = paymentService.pay(userId, request);

        // Then
        assertThat(paid).isNotNull();
        assertThat(paid.payAmount()).isEqualTo(89_000L);
        assertThat(paid.paymentMethod()).isEqualTo("CARD");
        verify(userPointManager, atLeastOnce()).usePoint(any(), any());
    }

}
