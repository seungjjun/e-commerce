package com.hanghae.ecommerce.domain.product;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hanghae.ecommerce.domain.cart.NewCartItem;
import com.hanghae.ecommerce.domain.order.Order;

@Service
public class ProductService {
	private final ProductReader productReader;
	private final ProductUpdator productUpdator;
	private final ProductValidator productValidator;

	public ProductService(
		ProductReader productReader,
		ProductUpdator productUpdator,
		ProductValidator productValidator) {
		this.productReader = productReader;
		this.productUpdator = productUpdator;
		this.productValidator = productValidator;
	}

	public List<Product> getProducts() {
		return productReader.readAll();
	}

	public Product getProductDetail(Long productId) {
		return productReader.readById(productId);
	}

	public List<Product> getPopularProducts() {
		return productReader.readPopularProducts();
	}

	public List<Product> getProductsByIds(List<Long> productIds) {
		return productReader.readAllByIds(productIds);
	}

	public void checkProductStockForAddToCart(List<NewCartItem> cartItems) {
		productValidator.checkPossibleAddToCart(cartItems);
	}

	public void updateStockQuantity(List<Product> products, Order order) {
		productUpdator.updateStock(products, order);
	}
}
