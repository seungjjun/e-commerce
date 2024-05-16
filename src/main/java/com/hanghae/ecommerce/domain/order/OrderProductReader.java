package com.hanghae.ecommerce.domain.order;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hanghae.ecommerce.domain.cart.Cart;
import com.hanghae.ecommerce.domain.cart.CartItem;
import com.hanghae.ecommerce.domain.product.ProductReader;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderProductReader {

	private final ProductReader productReader;

	public List<OrderProduct> read(Cart cart) {
		List<OrderProduct> orderProducts = new ArrayList<>();
		if (cart.items().isEmpty()) {
			throw new EntityNotFoundException("장바구니에 담긴 상품이 존재하지 않습니다.");
		}

		productReader.readAllByIds(cart.items().stream().map(CartItem::productId).toList())
			.forEach(product -> {
				CartItem item = cart.items().stream().filter(cartItem -> cartItem.productId().equals(product.id()))
					.findFirst()
					.orElseThrow(() -> new EntityNotFoundException("장바구니에 담긴 상품이 존재하지 않습니다."));
				orderProducts.add(OrderProduct.of(product, item));
			});
		return orderProducts;
	}
}
