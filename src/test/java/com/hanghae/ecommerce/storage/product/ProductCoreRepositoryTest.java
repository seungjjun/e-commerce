package com.hanghae.ecommerce.storage.product;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class ProductCoreRepositoryTest {
    private ProductCoreRepository productCoreRepository;

    private ProductJpaRepository productJpaRepository;

    @BeforeEach
    void setUp() {
        productJpaRepository = mock(ProductJpaRepository.class);

        productCoreRepository = new ProductCoreRepository(productJpaRepository);
    }

    @Test
    @DisplayName("상품을 찾지 못했을 경우 에러가 발생한다.")
    void when_not_found_product_then_error() {
        // Given
        Long productId = 999L;

        // When && Then
        assertThrows(EntityNotFoundException.class, () -> {
            productCoreRepository.findById(productId);
        });
    }

}
