package com.hanghae.ecommerce.storage.payment;

import com.hanghae.ecommerce.api.error.UnsupportedPaymentMethodException;
import java.util.Arrays;
import java.util.NoSuchElementException;

import lombok.Getter;

@Getter
public enum PayType {
	CARD("CARD", 1, "카드 결제"),
	TRANSFER("TRANSFER", 2, "계좌 이체"),
	MOBILE_PAY("MOBILE PAY", 3, "모바일 결제");

	private final String method;
	private final Integer code;
	private final String description;

	PayType(String method, Integer code, String description) {
		this.method = method;
		this.code = code;
		this.description = description;
	}

	public String toString() {
		return method;
	}

	public static PayType of(String method) {
		return Arrays.stream(PayType.values())
			.filter(payType -> payType.method.equals(method))
			.findFirst()
			.orElseThrow(() -> new UnsupportedPaymentMethodException("존재하지 않는 결제 수단입니다."));
	}
}
