package com.hanghae.ecommerce.domain.orderitem;

import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.product.Product;
import com.hanghae.ecommerce.storage.orderitem.OrderItemEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderItemAppender {
    private final OrderItemRepository orderItemRepository;

    public OrderItemAppender(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional
    public List<OrderItem> create(Order order,
                                  List<Product> products,
                                  List<OrderRequest.ProductOrderRequest> productOrderRequests) {
        List<OrderItemEntity> orderItemEntities = new ArrayList<>();

        for (OrderRequest.ProductOrderRequest orderRequest : productOrderRequests) {
            Product product = products.stream()
                    .filter(p -> p.id().equals(orderRequest.id()))
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException(orderRequest.id() + " 상품의 정보를 찾지 못했습니다."));

            orderItemEntities.add(
                    new OrderItemEntity(
                            order.id(),
                            product.id(),
                            product.name(),
                            product.price(),
                            product.orderTotalPrice(orderRequest.quantity()),
                            orderRequest.quantity()
                    )
            );
        }
        return orderItemRepository.createOrderItem(orderItemEntities);
    }
}
