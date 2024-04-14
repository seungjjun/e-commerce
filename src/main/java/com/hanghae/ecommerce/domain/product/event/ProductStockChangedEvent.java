package com.hanghae.ecommerce.domain.product.event;

import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.domain.product.Product;
import com.hanghae.ecommerce.domain.product.Stock;
import org.springframework.context.ApplicationEvent;

import java.util.List;

public record ProductStockChangedEvent(List<Product> products, OrderRequest request) {
}
