package com.hanghae.ecommerce.api.controller;

import com.hanghae.ecommerce.api.dto.request.ChargingAmountRequest;
import com.hanghae.ecommerce.api.dto.response.BalanceResponse;
import com.hanghae.ecommerce.api.dto.response.ChargeResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("accounts")
public class AccountController {

    @GetMapping("/{userId}/balance")
    public BalanceResponse getBalance(@PathVariable Long userId) {
        return new BalanceResponse(50_000L);
    }

    @PatchMapping("/{userId}/charge")
    public ChargeResponse charge(@PathVariable Long userId,
                                 @Valid @RequestBody ChargingAmountRequest request) {
        return new ChargeResponse(70_000L);
    }
}
