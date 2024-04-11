package com.hanghae.ecommerce.domain.product;

import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.orderitem.OrderItem;
import com.hanghae.ecommerce.domain.orderitem.OrderItemReader;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductManager {
    private final OrderItemReader orderItemReader;
    private final ProductReader productReader;
    private final ProductUpdater productUpdater;

    public ProductManager(OrderItemReader orderItemReader, ProductReader productReader, ProductUpdater productUpdater) {
        this.orderItemReader = orderItemReader;
        this.productReader = productReader;
        this.productUpdater = productUpdater;
    }

    public void compensateProduct(Order order) {
        Long orderId = order.id();

        List<OrderItem> orderItemList = orderItemReader.readAllByOrderId(orderId);

        for (OrderItem orderItem : orderItemList) {
            Product product = productReader.readById(orderItem.productId());
            Product increasedStockProduct = product.increaseStock(orderItem.quantity());
            productUpdater.updateStock(increasedStockProduct);
        }
    }
}
