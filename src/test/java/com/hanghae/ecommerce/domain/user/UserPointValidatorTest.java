package com.hanghae.ecommerce.domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class UserPointValidatorTest {
    private UserPointValidator userPointValidator;
    private User user;

    @BeforeEach
    void setUp() {
        user = Mockito.mock(User.class);

        userPointValidator = new UserPointValidator();
    }

    @Test
    @DisplayName("주문 결제 시 사용자의 포인트를 검증하는 메서드가 호출되는지 테스트")
    void when_using_user_point_for_pay_then_call_is_enough_point_for_pay() {
        Long payAmount = 1000L;

        userPointValidator.checkUserPointForPay(user, payAmount);

        verify(user, times(1)).isEnoughPointForPay(payAmount);
    }
}
