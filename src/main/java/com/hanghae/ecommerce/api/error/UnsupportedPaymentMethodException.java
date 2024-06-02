package com.hanghae.ecommerce.api.error;

public class UnsupportedPaymentMethodException extends RuntimeException {
	public UnsupportedPaymentMethodException(String message) {
		super(message);
	}
}
