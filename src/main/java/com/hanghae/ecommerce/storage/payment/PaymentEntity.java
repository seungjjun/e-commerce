package com.hanghae.ecommerce.storage.payment;

import com.hanghae.ecommerce.storage.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentEntity extends BaseEntity {
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "pay_amount")
    private Long payAmount;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PayType paymentMethod;
}
