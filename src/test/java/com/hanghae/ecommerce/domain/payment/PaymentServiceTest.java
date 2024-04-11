package com.hanghae.ecommerce.domain.payment;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.api.dto.request.Receiver;
import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.order.OrderUpdater;
import com.hanghae.ecommerce.domain.order.OrderValidator;
import com.hanghae.ecommerce.domain.user.User;
import com.hanghae.ecommerce.domain.user.UserPointManager;
import com.hanghae.ecommerce.domain.user.UserPointValidator;
import com.hanghae.ecommerce.storage.order.OrderStatus;
import com.hanghae.ecommerce.storage.payment.PayType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class PaymentServiceTest {

    private PaymentService paymentService;
    private OrderUpdater orderUpdater;
    private OrderValidator orderValidator;
    private PaymentAppender paymentAppender;
    private UserPointManager userPointManager;
    private UserPointValidator userPointValidator;

    private Long userId;
    private Long orderId;
    private Long payAmount;
    private User user;
    private OrderRequest request;

    @BeforeEach
    void setUp() {
        orderUpdater = mock(OrderUpdater.class);
        orderValidator = mock(OrderValidator.class);
        paymentAppender = mock(PaymentAppender.class);
        userPointManager = mock(UserPointManager.class);
        userPointValidator = mock(UserPointValidator.class);

        paymentService = new PaymentService(orderUpdater, orderValidator, paymentAppender, userPointManager, userPointValidator);

        userId = 1L;
        orderId = 1L;
        payAmount = 50_000L;
        String paymentMethod = PayType.CARD.toString();

        user = Fixtures.user(userId);

        request = new OrderRequest(new Receiver(
                user.name(),
                user.address(),
                user.phoneNumber()),
                List.of(new OrderRequest.ProductOrderRequest(1L, 1L)),
                payAmount,
                paymentMethod
        );
    }

    @Test
    @DisplayName("카드 결제 성공")
    void succeed_pay_with_card() {
        // Given
        Payment payment = Fixtures.payment(orderId);
        Order order = Fixtures.order(OrderStatus.WAITING_FOR_PAY);

        given(orderValidator.isOrderStatusWaitingForPay(any())).willReturn(true);
        given(paymentAppender.create(any(), any(), any())).willReturn(payment);

        // When
        Payment paid = paymentService.pay(user, order, request);

        // Then
        assertThat(paid).isNotNull();
        assertThat(paid.payAmount()).isEqualTo(89_000L);
        assertThat(paid.paymentMethod()).isEqualTo("CARD");
        verify(userPointManager, atLeastOnce()).usePoint(any(), any());
    }

    @Test
    @DisplayName("주문 상태가 결제 대기 상태가 아닌 경우 결제는 실패한다.")
    void when_order_status_is_not_waiting_for_pay_then_failed_pay() {
        // Given
        Order order = Fixtures.order(OrderStatus.PAY_FAILED);

        given(orderValidator.isOrderStatusWaitingForPay(any())).willReturn(false);

        // When && Then
        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.pay(user, order, request);
        });
    }
}
