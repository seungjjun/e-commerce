package com.hanghae.ecommerce.storage.cart;

import com.hanghae.ecommerce.domain.cart.Cart;
import com.hanghae.ecommerce.domain.cart.CartItemRepository;
import com.hanghae.ecommerce.domain.cart.NewCartItem;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CartItemCoreRepository implements CartItemRepository {
    private final CartItemJpaRepository cartItemJpaRepository;

    public CartItemCoreRepository(CartItemJpaRepository cartItemJpaRepository) {
        this.cartItemJpaRepository = cartItemJpaRepository;
    }

    @Override
    public Optional<CartItemEntity> findByCartIdAndProductId(Long cartId, Long productId) {
        return cartItemJpaRepository.findByCartIdAndProductId(cartId, productId);
    }

    @Override
    public void addItem(Cart cart, NewCartItem cartItem) {
        cartItemJpaRepository.save(new CartItemEntity(cart.id(), cartItem.productId(), cartItem.quantity()));
    }
}
