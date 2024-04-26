package com.hanghae.ecommerce.api.dto.response;

public record ChargeResponse(Long balance) {
	public static ChargeResponse from(Long balance) {
		return new ChargeResponse(balance);
	}
}
