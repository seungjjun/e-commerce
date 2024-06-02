package com.hanghae.ecommerce.api.error;

public class InvalidQuantityException extends RuntimeException {
	public InvalidQuantityException(String message) {
		super(message);
	}
}
