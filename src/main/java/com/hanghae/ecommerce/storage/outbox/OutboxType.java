package com.hanghae.ecommerce.storage.outbox;

public enum OutboxType {
	ORDER("ORDER"),
	PAYMENT("PAYMENT");

	private final String value;

	OutboxType(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}
