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

class ProductReaderTest {
    private ProductReader productReader;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);

        productReader = new ProductReader(productRepository);
    }

    @Test
    @DisplayName("상품 목록을 불러온다.")
    void readAll() {
        // Given
        Product product1 = Fixtures.product("후드티");
        Product product2 = Fixtures.product("맨투맨");

        given(productRepository.findAll()).willReturn(List.of(product1, product2));

        // When
        List<Product> products = productReader.readAll();

        // Then
        assertThat(products.size()).isEqualTo(2);
        assertThat(products.get(0).name()).isEqualTo("후드티");
        assertThat(products.get(1).name()).isEqualTo("맨투맨");
    }

    @Test
    @DisplayName("상품 삼세 정보를 조회한다.")
    void readProduct() {
        // Given
        Long productId = 2L;

        Product product = Fixtures.product("맨투맨");

        given(productRepository.findById(any())).willReturn(product);

        // When
        Product foundProduct = productReader.readById(productId);

        // Then
        assertThat(foundProduct.name()).isEqualTo("맨투맨");
        assertThat(foundProduct.description()).isEqualTo("늘어나지 않는 맨투맨");
    }

}
