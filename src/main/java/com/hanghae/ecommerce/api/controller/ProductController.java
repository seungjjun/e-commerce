package com.hanghae.ecommerce.api.controller;

import com.hanghae.ecommerce.api.dto.response.ProductDetailResponse;
import com.hanghae.ecommerce.api.dto.response.ProductListResponse;
import com.hanghae.ecommerce.api.dto.response.ProductSummaryResponse;
import com.hanghae.ecommerce.domain.product.Product;
import com.hanghae.ecommerce.domain.product.ProductCoreService;
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

    @GetMapping
    public ProductListResponse getProducts() {
        List<Product> products = productService.getProducts();

        List<ProductSummaryResponse> productSummaryResponseList = products.stream()
                .map(ProductSummaryResponse::from)
                .toList();
        return new ProductListResponse(productSummaryResponseList);
    }

    @GetMapping("/{id}")
    public ProductDetailResponse getProduct(@PathVariable Long id) {
        return new ProductDetailResponse(id, "후드티", 70_000L, 7L, "편한 후드티");
    }

    @GetMapping("/popular")
    public ProductListResponse popularProducts() {
        return new ProductListResponse(
                List.of(
                        new ProductSummaryResponse(1L, "후드티", 70_000L),
                        new ProductSummaryResponse(2L, "맨투맨", 59_000L),
                        new ProductSummaryResponse(3L, "슬랙스", 49_000L),
                        new ProductSummaryResponse(4L, "반팔티", 19_000L),
                        new ProductSummaryResponse(5L, "모자", 39_000L)
                ));
    }
}
