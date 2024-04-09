package com.hanghae.ecommerce.api.controller;

import com.hanghae.ecommerce.api.dto.request.PaymentRequest;
import com.hanghae.ecommerce.api.dto.response.PaymentResponse;
import com.hanghae.ecommerce.domain.payment.Payment;
import com.hanghae.ecommerce.domain.payment.PaymentCoreService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("payments")
public class PaymentController {

    private final PaymentCoreService paymentCoreService;

    public PaymentController(PaymentCoreService paymentCoreService) {
        this.paymentCoreService = paymentCoreService;
    }

    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponse pay(@PathVariable Long userId,
                               @Valid @RequestBody PaymentRequest request) {
        Payment payment = paymentCoreService.pay(userId, request);
        return PaymentResponse.from(payment);
    }
}
