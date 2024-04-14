package com.hanghae.ecommerce.domain.product;

import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductReader productReader;
    private final ProductUpdator productUpdator;

    public ProductService(ProductReader productReader, ProductUpdator productUpdator) {
        this.productReader = productReader;
        this.productUpdator = productUpdator;
    }

    public List<Product> getProducts() {
        return productReader.readAll();
    }

    public Product getProductDetail(Long productId) {
        return productReader.readById(productId);
    }

    public List<Product> getPopularProducts() {
        return productReader.readPopularProducts();
    }

    public List<Product> getProductsByIds(List<OrderRequest.ProductOrderRequest> products) {
        return productReader.readAllByIds(products);
    }

    public void updateStockQuantity(List<Product> products, List<OrderRequest.ProductOrderRequest> request) {
        productUpdator.updateStock(products, request);
    }
}
