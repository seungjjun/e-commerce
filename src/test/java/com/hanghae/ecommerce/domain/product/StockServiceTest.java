package com.hanghae.ecommerce.domain.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.api.dto.request.Receiver;

class StockServiceTest {
	private StockService stockService;
	private StockReader stockReader;
	private StockValidator stockValidator;
	private StockUpdator stockUpdator;

	@BeforeEach
	void setUp() {
		stockReader = mock(StockReader.class);
		stockValidator = mock(StockValidator.class);
		stockUpdator = mock(StockUpdator.class);

		stockService = new StockService(stockReader, stockValidator, stockUpdator);
	}

	@Test
	@DisplayName("상품 재고를 조회한다.")
	void getProductStockQuantity() {
		// Given
		List<Product> products = List.of(Fixtures.product("후드티"));

		given(stockReader.readByProductIds(anyList())).willReturn(List.of(
			Fixtures.stock(products.get(0).id())
		));

		// When
		List<Stock> stocks = stockService.getStocksByProductIds(products);

		// Then
		assertThat(stocks.size()).isEqualTo(1);
		assertThat(stocks.getFirst().productId()).isEqualTo(1L);
		assertThat(stocks.getFirst().stockQuantity()).isEqualTo(5L);
	}

	@Test
	@DisplayName("상품 재고를 감소시킨다.")
	void decreaseProductStockQuantity() {
		// Given
		List<Stock> stocks = List.of(
			Fixtures.stock(1L),
			Fixtures.stock(2L)
		);

		OrderRequest request = new OrderRequest(
			new Receiver(
				"홍길동",
				"서울 송파",
				"01012345678"
			),
			List.of(
				new OrderRequest.ProductOrderRequest(1L, 1L),
				new OrderRequest.ProductOrderRequest(2L, 10L)
			),
			50_000L,
			"CARD"
		);

		given(stockUpdator.updateStockForOrder(anyList(), anyList())).willReturn(List.of(
			Fixtures.stock(1L).decreaseStock(request.products().get(0).quantity()),
			Fixtures.stock(2L).decreaseStock(request.products().get(1).quantity())
		));

		// When
		List<Stock> decreaseProductStock = stockService.decreaseProductStock(List.of(), request);

		// Then
		assertThat(decreaseProductStock.size()).isEqualTo(2);
		assertThat(decreaseProductStock.getFirst().stockQuantity()).isEqualTo(5 - 1);
		assertThat(decreaseProductStock.getLast().stockQuantity()).isEqualTo(10L - 10L);
	}
}
