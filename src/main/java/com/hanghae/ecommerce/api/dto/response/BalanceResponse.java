package com.hanghae.ecommerce.api.dto.response;

public record BalanceResponse(Long balance) {
    public static BalanceResponse from(Long balance) {
        return new BalanceResponse(balance);
    }
}
