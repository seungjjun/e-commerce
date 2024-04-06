package com.hanghae.ecommerce.domain.product;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements ProductCoreService {
    private final ProductReader productReader;

    public ProductService(ProductReader productReader) {
        this.productReader = productReader;
    }

    @Override
    public List<Product> getProducts() {
        return productReader.readAll();
    }
}
