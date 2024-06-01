package com.hanghae.ecommerce.domain.order;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.domain.product.ProductReader;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderProductReader {

	private final ProductReader productReader;

	public List<OrderProduct> read(List<OrderRequest.ProductRequest> productRequests) {
		List<OrderProduct> orderProducts = new ArrayList<>();
		productReader.readAllByIds(productRequests.stream().map(OrderRequest.ProductRequest::id).toList())
			.forEach(p -> {
				OrderRequest.ProductRequest productRequest =
					productRequests.stream().filter(request -> p.id().equals(request.id())).findFirst().get();
				orderProducts.add(OrderProduct.of(p, productRequest.quantity()));
			});
		return orderProducts;
	}
}
