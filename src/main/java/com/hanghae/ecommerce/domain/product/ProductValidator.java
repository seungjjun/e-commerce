package com.hanghae.ecommerce.domain.product;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hanghae.ecommerce.domain.cart.NewCartItem;

import jakarta.persistence.EntityNotFoundException;

@Component
public class ProductValidator {
	private final ProductRepository productRepository;

	public ProductValidator(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public void checkPossibleAddToCart(List<NewCartItem> cartItems) {
		for (NewCartItem newCartItem : cartItems) {
			Product product = productRepository.findById(newCartItem.productId())
				.orElseThrow(() -> new EntityNotFoundException("상품 정보를 찾지 못했습니다. - id: " + newCartItem.productId()))
				.toProduct();

			product.isEnoughStockQuantity(newCartItem.quantity());
		}
	}
}
