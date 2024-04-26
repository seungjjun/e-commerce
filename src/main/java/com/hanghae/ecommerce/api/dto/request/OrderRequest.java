package com.hanghae.ecommerce.api.dto.request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrderRequest(@Valid Receiver receiver,
							@Valid List<ProductOrderRequest> products,
							@NotNull Long paymentAmount,
							@NotBlank String paymentMethod) {
	public record ProductOrderRequest(
		@NotNull Long id,
		@NotNull Long quantity
	) {
	}
}
