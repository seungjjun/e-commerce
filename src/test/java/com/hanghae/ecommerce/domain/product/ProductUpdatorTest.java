package com.hanghae.ecommerce.domain.product;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

class ProductUpdatorTest {
    private ProductUpdator productUpdator;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);

        productUpdator = new ProductUpdator(productRepository);
    }

    @Test
    @DisplayName("상품의 재고를 업데이트 한다.")
    void update_product_stock() {
        // Given
        List<Product> products = List.of(
                Fixtures.product("후드티"),
                Fixtures.product("맨투맨")
        );

        List<OrderRequest.ProductOrderRequest> productsOrderRequest = List.of(
                new OrderRequest.ProductOrderRequest(1L, 5L),
                new OrderRequest.ProductOrderRequest(2L, 10L)
        );

        // When
        productUpdator.updateStock(products, productsOrderRequest);

        // Then
        verify(productRepository, atLeast(2)).updateStock(any());
    }

}
