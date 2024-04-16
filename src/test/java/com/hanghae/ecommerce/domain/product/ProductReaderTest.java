package com.hanghae.ecommerce.domain.product;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.storage.product.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

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

        given(productRepository.findById(any())).willReturn(Optional.of(new ProductEntity(product.name(), product.price(), product.description(), product.stockQuantity())));

        // When
        Product foundProduct = productReader.readById(productId);

        // Then
        assertThat(foundProduct.name()).isEqualTo("맨투맨");
        assertThat(foundProduct.description()).isEqualTo("늘어나지 않는 맨투맨");
    }

    @Test
    @DisplayName("상품 id list 기반으로 상품을 조회한다.")
    void readAllProductByIds() {
        // Given
        List<OrderRequest.ProductOrderRequest> productOrderRequests = List.of(
                new OrderRequest.ProductOrderRequest(1L, 1L),
                new OrderRequest.ProductOrderRequest(2L, 5L)
        );

        List<Product> products = List.of(
                Fixtures.product("후드티"),
                Fixtures.product("맨투맨")
        );

        List<Long> productIds = productOrderRequests.stream().map(OrderRequest.ProductOrderRequest::id).toList();

        given(productRepository.findByIdIn(any())).willReturn(products);

        // When
        List<Product> foundProducts = productReader.readAllByIds(productIds);

        // Then
        assertThat(foundProducts).isNotNull();
        assertThat(foundProducts.get(0).name()).isEqualTo("후드티");
        assertThat(foundProducts.get(1).name()).isEqualTo("맨투맨");
    }

    @Test
    @DisplayName("최근 3일 동안 가장 많이 팔린 상품을 조회한다.")
    void read_recent_three_days_popular_products() {
        // Given
        given(productRepository.findTopSellingProducts(any(), any(), any(), any())).willReturn(
                List.of(Fixtures.product("후드티"),
                        Fixtures.product("맨투맨"),
                        Fixtures.product("슬랙스"),
                        Fixtures.product("백팩"),
                        Fixtures.product("모자"))
        );

        // When
        List<Product> popularProducts = productReader.readPopularProducts();

        // Then
        assertThat(popularProducts.size()).isEqualTo(5);
        assertThat(popularProducts.getFirst().name()).isEqualTo("후드티");
        assertThat(popularProducts.getLast().name()).isEqualTo("모자");
    }
}
