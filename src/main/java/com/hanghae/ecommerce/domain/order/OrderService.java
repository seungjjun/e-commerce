package com.hanghae.ecommerce.domain.order;

import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.domain.orderitem.OrderItemAppender;
import com.hanghae.ecommerce.domain.product.Product;
import com.hanghae.ecommerce.domain.product.ProductReader;
import com.hanghae.ecommerce.domain.product.ProductValidator;
import com.hanghae.ecommerce.domain.user.User;
import com.hanghae.ecommerce.domain.user.UserReader;
import com.hanghae.ecommerce.storage.order.OrderStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService implements OrderCoreService {
    private final UserReader userReader;
    private final ProductReader productReader;
    private final OrderItemAppender orderItemAppender;
    private final OrderProcessor orderProcessor;
    private final OrderUpdater orderUpdater;
    private final ProductValidator productValidator;

    public OrderService(UserReader userReader, ProductReader productReader, OrderItemAppender orderItemAppender, OrderProcessor orderProcessor, OrderUpdater orderUpdater, ProductValidator productValidator) {
        this.userReader = userReader;
        this.productReader = productReader;
        this.orderItemAppender = orderItemAppender;
        this.orderProcessor = orderProcessor;
        this.orderUpdater = orderUpdater;
        this.productValidator = productValidator;
    }

    @Override
    public Order order(Long userId, OrderRequest request) {
        User user = userReader.readById(userId);

        Order order = orderProcessor.order(user, request);

        List<Product> products = productReader.readAllByIds(request.products());

        productValidator.checkProductStockQuantityForOrder(order, products, request.products());

        orderItemAppender.create(order, products, request.products());
        return orderUpdater.changeStatus(order, OrderStatus.COMPLETE);
    }
}
