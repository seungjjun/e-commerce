package com.hanghae.ecommerce.domain.cart;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hanghae.ecommerce.domain.user.User;

@Service
public class CartService {

	private final CartFinder cartFinder;
	private final CartItemFinder cartItemFinder;
	private final CartItemAppender cartItemAppender;
	private final CartItemRemover cartItemRemover;

	public CartService(CartFinder cartFinder,
					   CartItemFinder cartItemFinder,
					   CartItemAppender cartItemAppender,
					   CartItemRemover cartItemRemover) {
		this.cartFinder = cartFinder;
		this.cartItemFinder = cartItemFinder;
		this.cartItemAppender = cartItemAppender;
		this.cartItemRemover = cartItemRemover;
	}

	@Transactional(readOnly = true)
	public Cart getCart(User user) {
		return cartFinder.findByUserId(user.id());
	}

	public void addItemToCart(Cart cart, List<NewCartItem> newCartItem) {
		cartItemAppender.addItem(cart, newCartItem);
	}

	public List<CartItem> getAllCartItems(Cart cart) {
		return cartItemFinder.findAllByCartId(cart.id());
	}

	public List<CartItem> getCartItemsByIds(Cart cart, List<Long> cartItemIds) {
		List<CartItem> cartItems = cartItemFinder.findAllByCartId(cart.id());
		return cartItems.stream()
			.filter(item -> cartItemIds.stream().anyMatch(id -> id.equals(item.id())))
			.toList();
	}

	public void deleteItem(List<CartItem> cartItems) {
		cartItemRemover.removeItems(cartItems);
	}

	@Transactional
	public void resetCart(User user) {
		cartItemRemover.resetCart(user);
	}
}
