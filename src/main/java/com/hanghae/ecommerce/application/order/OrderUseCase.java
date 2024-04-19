package com.hanghae.ecommerce.application.order;

import com.hanghae.ecommerce.api.dto.OrderPaidResult;
import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.order.OrderService;
import com.hanghae.ecommerce.domain.order.event.OrderCreatedEvent;
import com.hanghae.ecommerce.domain.product.Product;
import com.hanghae.ecommerce.domain.product.ProductService;
import com.hanghae.ecommerce.domain.user.User;
import com.hanghae.ecommerce.domain.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Slf4j
public class OrderUseCase {

    private final UserService userService;
    private final ProductService productService;
    private final OrderService orderService;
    private final ApplicationEventPublisher applicationEventPublisher;

    public OrderUseCase(UserService userService,
                        ProductService productService,
                        OrderService orderService,
                        ApplicationEventPublisher applicationEventPublisher) {
        this.userService = userService;
        this.productService = productService;
        this.orderService = orderService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Transactional
    public OrderPaidResult order(Long userId, OrderRequest request) {
        User user = userService.getUser(userId);
        List<Product> products = productService.getProductsByIds(request.products().stream()
                .map(OrderRequest.ProductOrderRequest::id)
                .toList()
        );

        Order order = orderService.order(user, products, request);

        log.info("------주문 생성------");
        applicationEventPublisher.publishEvent(new OrderCreatedEvent(user, products, request, order));
        return OrderPaidResult.from(order);
    }
}
