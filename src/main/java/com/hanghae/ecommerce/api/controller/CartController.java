package com.hanghae.ecommerce.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hanghae.ecommerce.api.dto.request.CartItemRequest;
import com.hanghae.ecommerce.api.dto.request.DeleteCartItemRequest;
import com.hanghae.ecommerce.api.dto.response.AddCartItemResponse;
import com.hanghae.ecommerce.api.dto.response.CartItemResponse;
import com.hanghae.ecommerce.api.dto.response.CartItemResult;
import com.hanghae.ecommerce.api.dto.response.DeleteCartItemResponse;
import com.hanghae.ecommerce.application.cart.CartUseCase;

@RestController
@RequestMapping("/cart-items")
public class CartController {

	private final CartUseCase cartUseCase;

	public CartController(CartUseCase cartUseCase) {
		this.cartUseCase = cartUseCase;
	}

	@GetMapping("/{userId}")
	public CartItemResponse getCartItems(@PathVariable Long userId) {
		CartItemResult cartItemResult = cartUseCase.getCartItems(userId);
		return CartItemResponse.from(cartItemResult);
	}

	@PostMapping("/{userId}")
	@ResponseStatus(HttpStatus.CREATED)
	public AddCartItemResponse addCartItems(@PathVariable Long userId,
											@RequestBody CartItemRequest request) {
		cartUseCase.addItem(userId, request.toNewCartItem());
		return AddCartItemResponse.from("상품이 추가 되었습니다.");
	}

	@PostMapping("/delete/{userId}")
	public DeleteCartItemResponse deleteCartItems(@PathVariable Long userId,
													@RequestBody DeleteCartItemRequest request) {
		cartUseCase.deleteItem(userId, request.cartItemIdList());
		return DeleteCartItemResponse.from("상품이 삭제 되었습니다.");
	}
}
