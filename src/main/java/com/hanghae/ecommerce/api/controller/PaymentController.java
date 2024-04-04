package com.hanghae.ecommerce.api.controller;

import com.hanghae.ecommerce.api.dto.request.PaymentRequest;
import com.hanghae.ecommerce.api.dto.request.PaymentResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("payments")
public class PaymentController {

    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponse pay(@PathVariable Long userId,
                               @Valid @RequestBody PaymentRequest request) {
        return new PaymentResponse(
                "결제가 완료되었습니다.",
                "SUCCESS",
                "2024-04-02 13:00:00"
        );
    }
}
