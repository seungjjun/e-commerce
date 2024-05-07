package com.hanghae.ecommerce.storage.order;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum OrderStatus {
	READY("READY"),
	PAID("PAID"),
	PAY_FAILED("PAY FAILED"),
	FAIL("FAIL"),
	CANCELED("CANCELED");

	private final String value;

	OrderStatus(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}

	public static OrderStatus of(String value) {
		return Arrays.stream(OrderStatus.values())
			.filter(orderStatus -> orderStatus.value.equals(value))
			.findFirst()
			.orElseThrow(() -> new NoSuchElementException("존재 하지 않는 주문 상태 입니다."));
	}
}
