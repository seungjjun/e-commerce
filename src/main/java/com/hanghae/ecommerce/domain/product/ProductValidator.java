package com.hanghae.ecommerce.domain.product;

import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductValidator {

    public void checkProductStockQuantityForOrder(List<Product> products,
                                                     List<OrderRequest.ProductOrderRequest> productsOrderRequest) {
        for (OrderRequest.ProductOrderRequest orderRequest : productsOrderRequest) {
            Product product = products.stream()
                    .filter(p -> p.id().equals(orderRequest.id()))
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException(orderRequest.id() + " 상품의 정보를 찾지 못했습니다."));

            product.isEnoughProductStockQuantityForOrder(orderRequest.quantity());
        }
    }
}
