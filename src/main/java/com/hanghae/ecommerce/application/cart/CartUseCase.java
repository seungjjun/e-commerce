package com.hanghae.ecommerce.application.cart;

import com.hanghae.ecommerce.domain.cart.Cart;
import com.hanghae.ecommerce.domain.cart.CartItem;
import com.hanghae.ecommerce.domain.cart.CartService;
import com.hanghae.ecommerce.domain.cart.NewCartItem;
import com.hanghae.ecommerce.domain.product.ProductService;
import com.hanghae.ecommerce.domain.user.User;
import com.hanghae.ecommerce.domain.user.UserService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
