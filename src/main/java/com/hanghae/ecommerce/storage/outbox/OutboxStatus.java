package com.hanghae.ecommerce.storage.outbox;

public enum OutboxStatus {
	INIT("INIT"),
	PUBLISHED("PUBLISHED");

	private final String value;

	OutboxStatus(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}
