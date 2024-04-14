package com.hanghae.ecommerce.api.eventlistener;

import com.hanghae.ecommerce.domain.product.ProductService;
import com.hanghae.ecommerce.domain.product.event.ProductStockChangedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ProductEventHandler {
    private final ProductService productService;

    public ProductEventHandler(ProductService productService) {
        this.productService = productService;
    }

    @EventListener
    public void onProductStockChanged(ProductStockChangedEvent event) {
        productService.updateStockQuantity(event.products(), event.request().products());
    }
}
