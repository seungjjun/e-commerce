package com.hanghae.ecommerce.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.hanghae.ecommerce.api.dto.response.ErrorResponse;
import com.hanghae.ecommerce.api.error.InsufficientPointException;
import com.hanghae.ecommerce.api.error.InsufficientStockException;
import com.hanghae.ecommerce.api.error.InvalidQuantityException;
import com.hanghae.ecommerce.api.error.UnsupportedPaymentMethodException;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(InsufficientPointException.class)
	public ResponseEntity<ErrorResponse> handleInsufficientPointException(Exception e) {
		ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
		return ResponseEntity.badRequest().body(errorResponse);
	}

	@ExceptionHandler(InsufficientStockException.class)
	public ResponseEntity<ErrorResponse> handleInsufficientStockException(Exception e) {
		ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
		return ResponseEntity.badRequest().body(errorResponse);
	}

	@ExceptionHandler(InvalidQuantityException.class)
	public ResponseEntity<ErrorResponse> handleInvalidQuantityException(Exception e) {
		ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
		return ResponseEntity.badRequest().body(errorResponse);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
		ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}

	@ExceptionHandler(UnsupportedPaymentMethodException.class)
	public ResponseEntity<ErrorResponse> handleUnsupportedPaymentMethodException(EntityNotFoundException e) {
		ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}
}
