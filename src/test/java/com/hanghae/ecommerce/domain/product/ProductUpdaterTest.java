package com.hanghae.ecommerce.domain.product;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class ProductUpdaterTest {
    private ProductUpdater productUpdater;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);

        productUpdater = new ProductUpdater(productRepository);
    }

    @Test
    @DisplayName("상품 재고 업데이트 시 repository의 재고 업데이트 메서드가 호출된다.")
    void update_product_stock() {
        // Given
        Product product = Fixtures.product("슬랙스");

        // When
        productUpdater.updateStock(product);

        // Then
        verify(productRepository, atLeastOnce()).updateStock(any());
    }

    @Test
    @DisplayName("재고 업데이트 메서드 호출 시 product의 재고 감소 메서드가 호출 된다.")
    void update_product_stock_for_order() {
        // Given
        Product mockProduct1 = mock(Product.class);
        Product mockProduct2 = mock(Product.class);

        List<Product> products = List.of(
                mockProduct1,
                mockProduct2
        );

        List<OrderRequest.ProductOrderRequest> productsOrderRequest = List.of(
                new OrderRequest.ProductOrderRequest(1L, 5L),
                new OrderRequest.ProductOrderRequest(2L, 10L)
        );

        given(mockProduct1.id()).willReturn(1L);
        given(mockProduct2.id()).willReturn(2L);

        // When
        List<Product> updateStockProducts = productUpdater.updateStockForOrder(products, productsOrderRequest);

        // Then
        verify(productRepository, atLeastOnce()).updateStock(any());
        verify(mockProduct1, atLeastOnce()).decreaseStock(any());
        verify(mockProduct2, atLeastOnce()).decreaseStock(any());
        assertThat(updateStockProducts.size()).isEqualTo(2);
    }

}
