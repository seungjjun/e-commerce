package com.hanghae.ecommerce.domain.cart;

import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.order.OrderItem;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
	public Cart getCart(Long userId) {
		return cartFinder.findByUserId(userId);
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
	public void resetCart(Long userId) {
		cartItemRemover.resetCart(userId);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void compensateCartItems(Order order) {
		Cart cart = cartFinder.findByUserId(order.userId());
		List<NewCartItem> cartItems = new ArrayList<>();
		for (OrderItem orderItem : order.items()) {
			cartItems.add(new NewCartItem(orderItem.productId(), orderItem.quantity()));
		}
		cartItemAppender.addItem(cart, cartItems);
	}
}
