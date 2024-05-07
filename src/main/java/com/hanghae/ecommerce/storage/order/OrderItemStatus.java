package com.hanghae.ecommerce.storage.order;

public enum OrderItemStatus {
	CREATED("CREATED"),
	SUCCESS("SUCCESS"),
	FAIL("FAIL");

	private final String value;

	OrderItemStatus(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}
