package com.hanghae.ecommerce.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hanghae.ecommerce.api.dto.request.ChargingAmountRequest;
import com.hanghae.ecommerce.api.dto.response.BalanceResponse;
import com.hanghae.ecommerce.api.dto.response.ChargeResponse;
import com.hanghae.ecommerce.domain.user.UserPointService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("accounts")
public class AccountController {

	private final UserPointService userPointService;

	public AccountController(UserPointService userPointService) {
		this.userPointService = userPointService;
	}

	@Tag(name = "잔액 조회 API", description = "사용자의 잔액을 조회하는 API")
	@GetMapping("/{userId}/balance")
	public BalanceResponse getBalance(@PathVariable Long userId) {
		Long balance = userPointService.getPoint(userId);
		return BalanceResponse.from(balance);
	}

	@Tag(name = "잔액 충전 API", description = "사용자의 잔액을 충전하는 API")
	@PatchMapping("/{userId}/charge")
	public ChargeResponse charge(@PathVariable Long userId, @Valid @RequestBody ChargingAmountRequest request) {
		Long balance = userPointService.chargePoint(userId, request.amount());
		return ChargeResponse.from(balance);
	}
}
