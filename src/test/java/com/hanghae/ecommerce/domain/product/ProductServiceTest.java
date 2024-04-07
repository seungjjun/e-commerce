package com.hanghae.ecommerce.domain.product;

import com.hanghae.ecommerce.Fixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ProductServiceTest {
    private ProductService productService;
    private ProductReader productReader;

    @BeforeEach
    void setUp() {
        productReader = mock(ProductReader.class);

        productService = new ProductService(productReader);
    }

    @Test
    @DisplayName("상풍 목록을 조회한다.")
    void getProducts() {
        // Given
        Product product1 = Fixtures.product("후드티");
        Product product2 = Fixtures.product("맨투맨");

        given(productReader.readAll()).willReturn(List.of(product1, product2));

        // When
        List<Product> products = productService.getProducts();

        // Then
        assertThat(products).isNotNull();
        assertThat(products.size()).isEqualTo(2);
        assertThat(products.get(0).name()).isEqualTo("후드티");
        assertThat(products.get(1).name()).isEqualTo("맨투맨");
    }

    @Test
    @DisplayName("상품 상세 정보를 조회한다.")
    void getProductDetail() {
        // Given
        Long productId = 1L;

        Product product = Fixtures.product("후드티");

        given(productReader.readById(any())).willReturn(product);

        // When
        Product productDetail = productService.getProductDetail(productId);

        // Then
        assertThat(productDetail.name()).isEqualTo("후드티");
        assertThat(productDetail.stockQuantity()).isEqualTo(5L);
        assertThat(productDetail.description()).isEqualTo("그레이 후드티");
    }
}
