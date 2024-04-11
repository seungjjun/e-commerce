package com.hanghae.ecommerce.application.order;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.api.dto.OrderPaidResult;
import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.api.dto.request.Receiver;
import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.order.OrderCoreService;
import com.hanghae.ecommerce.domain.order.OrderService;
import com.hanghae.ecommerce.domain.payment.Payment;
import com.hanghae.ecommerce.domain.payment.PaymentCoreService;
import com.hanghae.ecommerce.domain.payment.PaymentService;
import com.hanghae.ecommerce.domain.product.Product;
import com.hanghae.ecommerce.domain.product.ProductCoreService;
import com.hanghae.ecommerce.domain.product.ProductService;
import com.hanghae.ecommerce.domain.user.User;
import com.hanghae.ecommerce.domain.user.UserCoreService;
import com.hanghae.ecommerce.domain.user.UserService;
import com.hanghae.ecommerce.storage.order.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class OrderUseCaseUnitTest {
    private UserCoreService userService;
    private ProductCoreService productService;
    private OrderCoreService orderService;
    private PaymentCoreService paymentService;
    private ApplicationEventPublisher applicationEventPublisher;

    private OrderUseCase orderUseCase;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        productService = mock(ProductService.class);
        orderService = mock(OrderService.class);
        paymentService = mock(PaymentService.class);
        applicationEventPublisher = mock(ApplicationEventPublisher.class);

        orderUseCase = new OrderUseCase(userService, productService, orderService, paymentService, applicationEventPublisher);
    }

    @Test
    @DisplayName("주문 및 결제 성공 테스트")
    void succeed_order() {
        // Given
        Long userId = 1L;
        User user = Fixtures.user(userId);
        List<Product> products = List.of(Fixtures.product("후드티"));
        Order order = Fixtures.order(OrderStatus.PAID);
        Payment payment = Fixtures.payment(order.id());
        OrderRequest request = new OrderRequest(
                new Receiver(
                        user.name(),
                        user.address(),
                        user.phoneNumber()
                ),
                List.of(
                        new OrderRequest.ProductOrderRequest(1L, 1L)
                ),
                50_000L,
                "CARD"
        );

        given(userService.getUser(any())).willReturn(user);
        given(productService.getProductsByIds(anyList())).willReturn(products);
        given(orderService.order(any(), anyList(), any())).willReturn(order);
        given(paymentService.pay(any(), any(), any())).willReturn(payment);

        // When
        OrderPaidResult orderPaidResult = orderUseCase.order(userId, request);

        // Then
        assertThat(orderPaidResult).isNotNull();
        assertThat(orderPaidResult.orderId()).isEqualTo(1L);
        assertThat(orderPaidResult.payAmount()).isEqualTo(89_000L);
        assertThat(orderPaidResult.paymentMethod()).isEqualTo("CARD");
    }

}
