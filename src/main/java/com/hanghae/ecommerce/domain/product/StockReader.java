package com.hanghae.ecommerce.domain.product;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StockReader {
    private final StockRepository stockRepository;

    public StockReader(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public List<Stock> readByProductIds(List<Product> products) {
        return stockRepository.findByProductIdIn(products.stream()
                .map(Product::id)
                .toList());
    }
}
