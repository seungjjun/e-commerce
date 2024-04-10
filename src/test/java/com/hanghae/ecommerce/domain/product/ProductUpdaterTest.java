package com.hanghae.ecommerce.domain.product;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

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
    @DisplayName("주문량 만큼 상품 재고 업데이트 성공")
    void update_product_stock_for_order() {
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
        productUpdater.updateStockForOrder(products, productsOrderRequest);

        // Then
        verify(productRepository, atLeastOnce()).updateStock(any());
    }

}
