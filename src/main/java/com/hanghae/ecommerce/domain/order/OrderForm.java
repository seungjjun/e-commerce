package com.hanghae.ecommerce.domain.order;

import java.util.List;

import com.hanghae.ecommerce.api.dto.request.OrderRequest;

public record OrderForm(
	Long payAmount,
	List<OrderProduct> orderProducts,
	String receiverName,
	String address,
	String phoneNumber,
	String paymentMethod
) {

	public static OrderForm of(OrderRequest request, List<OrderProduct> orderProducts) {
		return new OrderForm(
			request.paymentAmount(),
			orderProducts,
			request.receiver().name(),
			request.receiver().address(),
			request.receiver().phoneNumber(),
			request.paymentMethod());
	}
}
