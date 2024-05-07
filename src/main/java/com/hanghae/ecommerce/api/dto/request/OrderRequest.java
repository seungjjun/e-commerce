package com.hanghae.ecommerce.api.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrderRequest(
	@Valid Receiver receiver,
	@NotNull Long paymentAmount,
	@NotBlank String paymentMethod) {
}
