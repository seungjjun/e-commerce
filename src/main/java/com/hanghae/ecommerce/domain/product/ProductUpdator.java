package com.hanghae.ecommerce.domain.product;

import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Component;

import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.order.OrderItem;


@Component
public class ProductUpdator {
	private final ProductRepository productRepository;

	public ProductUpdator(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public void updateStock(Order order) {
		for (OrderItem item : order.items()) {
			Product product = productRepository.findById(item.productId());
			Product decreasedProduct = product.decreaseStock(item.quantity());
			updateProductStock(decreasedProduct);
		}
	}

	@CachePut(value = "products", key = "#product.id", unless = "#result == null", cacheManager = "cacheManager")
	public Product updateProductStock(Product product) {
		return productRepository.updateStock(product);
	}
}
