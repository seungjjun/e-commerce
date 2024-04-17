package com.hanghae.ecommerce.domain.product;

import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.domain.cart.NewCartItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductReader productReader;
    private final ProductUpdator productUpdator;
    private final ProductValidator productValidator;

    public ProductService(ProductReader productReader, ProductUpdator productUpdator, ProductValidator productValidator) {
        this.productReader = productReader;
        this.productUpdator = productUpdator;
        this.productValidator = productValidator;
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

    public List<Product> getProductsByIds(List<Long> productIds) {
        return productReader.readAllByIds(productIds);
    }

    public void updateStockQuantity(List<Product> products, List<OrderRequest.ProductOrderRequest> orderRequests) {
        productUpdator.updateStock(products, orderRequests);
    }

    public void checkProductStockForAddToCart(List<NewCartItem> cartItems) {
        productValidator.checkPossibleAddToCart(cartItems);
    }
}
