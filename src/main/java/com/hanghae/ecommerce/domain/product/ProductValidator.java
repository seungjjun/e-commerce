package com.hanghae.ecommerce.domain.product;

import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.order.OrderUpdater;
import com.hanghae.ecommerce.storage.order.OrderStatus;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductValidator {
    private final OrderUpdater orderUpdater;

    public ProductValidator(OrderUpdater orderUpdater) {
        this.orderUpdater = orderUpdater;
    }

    public void checkProductStockQuantityForOrder(Order order,
                                                  List<Product> products,
                                                  List<OrderRequest.ProductOrderRequest> productsOrderRequest) {
        for (OrderRequest.ProductOrderRequest orderRequest : productsOrderRequest) {
            Product product = products.stream()
                    .filter(p -> p.id().equals(orderRequest.id()))
                    .findFirst()
                    .orElseThrow(() -> {
                        orderUpdater.changeStatus(order, OrderStatus.CANCELED);
                        return new EntityNotFoundException(orderRequest.id() + " 상품의 정보를 찾지 못했습니다.");
                    });

            if (!product.isRemainingProductStock(orderRequest.quantity())) {
                orderUpdater.changeStatus(order, OrderStatus.CANCELED);
                throw new IllegalArgumentException(product.id() + " 상품의 재고가 부족합니다.");
            }
        }
    }
}
