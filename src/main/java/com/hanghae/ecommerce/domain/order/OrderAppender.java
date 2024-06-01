package com.hanghae.ecommerce.domain.order;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hanghae.ecommerce.api.dto.request.OrderRequest;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderAppender {

	private final OrderRepository orderRepository;
	private final OrderProductReader orderProductReader;

	public Order append(Long userId, OrderRequest request) {
		List<OrderProduct> orderProduct = orderProductReader.read(request.productRequests());
		OrderForm orderForm = OrderForm.of(request, orderProduct);
		return orderRepository.create(userId, orderForm);
	}
}
