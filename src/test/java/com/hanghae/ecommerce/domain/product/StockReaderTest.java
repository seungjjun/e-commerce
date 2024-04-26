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

class StockReaderTest {

	private StockReader stockReader;
	private StockRepository stockRepository;

	@BeforeEach
	void setUp() {
		stockRepository = mock(StockRepository.class);

		stockReader = new StockReader(stockRepository);
	}

	@Test
	@DisplayName("상품 재고를 가져온다.")
	void getProductStock() {
		// Given
		List<Product> products = List.of(
			Fixtures.product("후드티"),
			Fixtures.product("맨투맨")
		);

		given(stockRepository.findByProductIdIn(anyList())).willReturn(List.of(
			Fixtures.stock(products.get(0).id()),
			Fixtures.stock(products.get(1).id())
		));

		// When
		List<Stock> stocks = stockReader.readByProductIds(products);

		// Then
		assertThat(stocks.size()).isEqualTo(2L);
		assertThat(stocks.getFirst().productId()).isEqualTo(1L);
		assertThat(stocks.getLast().productId()).isEqualTo(2L);
	}
}
