package com.hanghae.ecommerce.api.error;

public class InsufficientPointException extends RuntimeException {
	public InsufficientPointException(String message) {
		super(message);
	}
}
