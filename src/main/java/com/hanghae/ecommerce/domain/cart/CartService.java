package com.hanghae.ecommerce.domain.cart;

import com.hanghae.ecommerce.domain.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartFinder cartFinder;
    private final CartItemAppender cartItemAppender;

    public CartService(CartFinder cartFinder, CartItemAppender cartItemAppender) {
        this.cartFinder = cartFinder;
        this.cartItemAppender = cartItemAppender;
    }

    public Cart getCart(User user) {
        return cartFinder.findByUserId(user.id());
    }

    public void addItemToCart(Cart cart, List<NewCartItem> newCartItem) {
        cartItemAppender.addItem(cart, newCartItem);
    }
}
