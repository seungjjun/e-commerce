package com.hanghae.ecommerce.domain.order;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.domain.cart.Cart;
import com.hanghae.ecommerce.domain.user.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderAppender {

	private final OrderRepository orderRepository;
	private final OrderProductReader orderProductReader;

	public Order append(User user, Cart cart, OrderRequest request) {
		List<OrderProduct> orderProduct = orderProductReader.read(cart);
		OrderForm orderForm = OrderForm.of(request, orderProduct);
		return orderRepository.create(user, orderForm);
	}
}