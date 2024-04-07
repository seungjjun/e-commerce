package com.hanghae.ecommerce.api.controller;

import com.hanghae.ecommerce.api.dto.request.ChargingAmountRequest;
import com.hanghae.ecommerce.api.dto.response.BalanceResponse;
import com.hanghae.ecommerce.api.dto.response.ChargeResponse;
import com.hanghae.ecommerce.domain.user.UserPointCoreService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("accounts")
public class AccountController {

    private final UserPointCoreService userPointCoreService;

    public AccountController(UserPointCoreService userPointCoreService) {
        this.userPointCoreService = userPointCoreService;
    }

    @GetMapping("/{userId}/balance")
    public BalanceResponse getBalance(@PathVariable Long userId) {
        Long balance = userPointCoreService.getPoint(userId);
        return BalanceResponse.from(balance);
    }

    @PatchMapping("/{userId}/charge")
    public ChargeResponse charge(@PathVariable Long userId,
                                 @Valid @RequestBody ChargingAmountRequest request) {
        Long balance = userPointCoreService.chargePoint(userId, request.amount());
        return ChargeResponse.from(balance);
    }
}
