package com.hanghae.ecommerce.application.order;

import com.hanghae.ecommerce.api.dto.OrderEventForStatistics;
import com.hanghae.ecommerce.api.dto.OrderPaidResult;
import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.order.OrderService;
import com.hanghae.ecommerce.domain.payment.Payment;
import com.hanghae.ecommerce.domain.payment.PaymentService;
import com.hanghae.ecommerce.domain.product.Product;
import com.hanghae.ecommerce.domain.product.ProductService;
import com.hanghae.ecommerce.domain.product.Stock;
import com.hanghae.ecommerce.domain.product.StockService;
import com.hanghae.ecommerce.domain.product.event.ProductStockChangedEvent;
import com.hanghae.ecommerce.domain.user.User;
import com.hanghae.ecommerce.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class OrderUseCase {

    private final UserService userService;
    private final ProductService productService;
    private final StockService stockService;
    private final OrderService orderService;
    private final PaymentService paymentService;
    private final ApplicationEventPublisher applicationEventPublisher;

    public OrderUseCase(UserService userService,
                        ProductService productService,
                        StockService stockService,
                        OrderService orderService,
                        PaymentService paymentService,
                        ApplicationEventPublisher applicationEventPublisher) {
        this.userService = userService;
        this.productService = productService;
        this.stockService = stockService;
        this.orderService = orderService;
        this.paymentService = paymentService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Transactional
    public OrderPaidResult order(Long userId, OrderRequest request) {
        User user = userService.getUser(userId);
        List<Product> products = productService.getProductsByIds(request.products());
        List<Stock> stocks = stockService.getStocksByProductIds(products);

        Order order = orderService.order(user, products, request);

        Payment payment = paymentService.pay(user, order, request);

        stockService.decreaseProductStock(stocks, request);

        applicationEventPublisher.publishEvent(new ProductStockChangedEvent(products, request));
        applicationEventPublisher.publishEvent(new OrderEventForStatistics(order, payment));
        return OrderPaidResult.of(order, payment);
    }
}
