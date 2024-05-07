package com.hanghae.ecommerce.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.api.dto.response.CartItemResult;
import com.hanghae.ecommerce.application.cart.CartUseCase;
import com.hanghae.ecommerce.domain.cart.CartItem;

@WebMvcTest(CartController.class)
class CartControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CartUseCase cartUseCase;

	@Test
	@DisplayName("장바구니 아이템 추가")
	void addCartItem() throws Exception {
		// Given
		Long userId = 1L;

		// When && Then
		mockMvc.perform(post("/cart-items/" + userId)
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{
						"cartItems": [
							{
								"productId": 1,
								"quantity": 1
							},
							{
								"productId": 2,
								"quantity": 10
							}
						]
					}
					"""))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.message").value("상품이 추가 되었습니다."));

		verify(cartUseCase, atLeastOnce()).addItem(any(), any());
	}

	@Test
	@DisplayName("장바구니 아이템 삭제")
	void deleteCartItem() throws Exception {
		// Given
		Long userId = 1L;

		// When && Then
		mockMvc.perform(post("/cart-items/delete/" + userId)
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{
						"cartItemIdList" : [1, 2]
					}
					"""))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.message").value("상품이 삭제 되었습니다."));

		verify(cartUseCase, atLeastOnce()).deleteItem(any(), any());
	}

	@Test
	@DisplayName("장바구니 아이템 조회")
	void getCartItems() throws Exception {
		// Given
		Long userId = 1L;

		CartItemResult cartItemResult = CartItemResult.of(
			List.of(new CartItem(1L, 2L, 2L)),
			List.of(Fixtures.product("맨투맨"))
		);

		given(cartUseCase.getCartItems(any())).willReturn(cartItemResult);

		// When && Then
		mockMvc.perform(get("/cart-items/" + userId))
			.andExpect(status().isOk())
			.andExpectAll(
				jsonPath("$.cartItems[0].productName").value("맨투맨"),
				jsonPath("$.cartItems[0].unitPrice").value("39000"),
				jsonPath("$.cartItems[0].quantity").value("2"),
				jsonPath("$.cartItems[0].totalPrice").value("78000"),
				jsonPath("$.totalPrice").value("78000")
			);
	}
}
