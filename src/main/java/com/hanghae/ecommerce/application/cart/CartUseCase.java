package com.hanghae.ecommerce.application.cart;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hanghae.ecommerce.api.dto.response.CartItemResult;
import com.hanghae.ecommerce.domain.cart.Cart;
import com.hanghae.ecommerce.domain.cart.CartItem;
import com.hanghae.ecommerce.domain.cart.CartService;
import com.hanghae.ecommerce.domain.cart.NewCartItem;
import com.hanghae.ecommerce.domain.product.Product;
import com.hanghae.ecommerce.domain.product.ProductService;
import com.hanghae.ecommerce.domain.user.User;
import com.hanghae.ecommerce.domain.user.UserService;

@Component
public class CartUseCase {
	private final UserService userService;
	private final CartService cartService;
	private final ProductService productService;

	public CartUseCase(UserService userService, CartService cartService, ProductService productService) {
		this.userService = userService;
		this.cartService = cartService;
		this.productService = productService;
	}

	public CartItemResult getCartItems(Long userId) {
		User user = userService.getUser(userId);

		Cart cart = cartService.getCart(user);

		List<CartItem> cartItems = cartService.getAllCartItems(cart);

		List<Product> products = productService.getProductsByIds(cartItems.stream().map(CartItem::productId).toList());

		return CartItemResult.of(cartItems, products);
	}

	@Transactional
	public void addItem(Long userId, List<NewCartItem> cartItems) {
		User user = userService.getUser(userId);

		Cart cart = cartService.getCart(user);

		productService.checkProductStockForAddToCart(cartItems);

		cartService.addItemToCart(cart, cartItems);
	}

	@Transactional
	public void deleteItem(Long userId, List<Long> cartItemIds) {
		User user = userService.getUser(userId);

		Cart cart = cartService.getCart(user);

		List<CartItem> cartItems = cartService.getCartItemsByIds(cart, cartItemIds);

		cartService.deleteItem(cartItems);
	}
}
