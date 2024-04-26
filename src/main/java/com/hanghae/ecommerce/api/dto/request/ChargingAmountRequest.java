package com.hanghae.ecommerce.api.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ChargingAmountRequest(
	@NotNull @Positive Long amount
) {
}
