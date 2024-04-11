package com.hanghae.ecommerce.api.controller;

import com.hanghae.ecommerce.api.dto.response.ProductDetailResponse;
import com.hanghae.ecommerce.api.dto.response.ProductListResponse;
import com.hanghae.ecommerce.api.dto.response.ProductSummaryResponse;
import com.hanghae.ecommerce.domain.product.Product;
import com.hanghae.ecommerce.domain.product.ProductCoreService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {
    private final ProductCoreService productService;

    public ProductController(ProductCoreService productService) {
        this.productService = productService;
    }

    @Tag(name = "상품 목록 조회 API", description = "등록된 상품 목록을 조회하는 API")
    @GetMapping
    public ProductListResponse getProducts() {
        List<Product> products = productService.getProducts();

        List<ProductSummaryResponse> productSummaryResponseList = products.stream()
                .map(ProductSummaryResponse::from)
                .toList();
        return new ProductListResponse(productSummaryResponseList);
    }

    @Tag(name = "상품 세부 정보 조회 API", description = "상품 세부 정보를 조회하는 API")
    @GetMapping("/{id}")
    public ProductDetailResponse getProduct(@PathVariable Long id) {
        Product product = productService.getProductDetail(id);
        return ProductDetailResponse.from(product);
    }

    @Tag(name = "인기 상품 조회 API", description = "최근 3일간 가장 많이 팔린 상품 목록을 조회하는 API")
    @GetMapping("/popular")
    public ProductListResponse popularProducts() {
        List<Product> products = productService.getPopularProducts();

        List<ProductSummaryResponse> productSummaryResponseList = products.stream()
                .map(ProductSummaryResponse::from)
                .toList();
        return new ProductListResponse(productSummaryResponseList);
    }
}
