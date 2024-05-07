package com.hanghae.ecommerce.domain.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

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
		Product product = Fixtures.product("후드티");
		Long stockQuantity = 5L;

		Stock stock = new Stock(1L, product.id(), stockQuantity);

		given(stockRepository.findByProductId(any())).willReturn(stock);

		// When
		Stock foundStock = stockReader.readByProductId(product.id());

		// Then
		assertThat(foundStock.productId()).isEqualTo(1L);
		assertThat(foundStock.stockQuantity()).isEqualTo(5L);
	}
}
