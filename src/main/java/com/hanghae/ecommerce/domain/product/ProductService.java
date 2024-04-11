package com.hanghae.ecommerce.domain.product;

import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements ProductCoreService {
    private final ProductReader productReader;
    private final ProductValidator productValidator;
    private final ProductUpdater productUpdater;

    public ProductService(ProductReader productReader, ProductValidator productValidator, ProductUpdater productUpdater) {
        this.productReader = productReader;
        this.productValidator = productValidator;
        this.productUpdater = productUpdater;
    }

    @Override
    public List<Product> getProducts() {
        return productReader.readAll();
    }

    @Override
    public Product getProductDetail(Long productId) {
        return productReader.readById(productId);
    }

    @Override
    public List<Product> getPopularProducts() {
        return productReader.readPopularProducts();
    }

    @Override
    public List<Product> getProductsByIds(List<OrderRequest.ProductOrderRequest> products) {
        return productReader.readAllByIds(products);
    }

    @Override
    public List<Product> decreaseStock(List<Product> products, OrderRequest request) {
        productValidator.checkProductStockQuantityForOrder(products, request.products());
        return productUpdater.updateStockForOrder(products, request.products());
    }
}
