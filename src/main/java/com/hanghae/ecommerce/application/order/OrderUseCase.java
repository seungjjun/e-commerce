package com.hanghae.ecommerce.application.order;

import com.hanghae.ecommerce.api.dto.OrderPaidResult;
import com.hanghae.ecommerce.api.dto.request.OrderRequest;
import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.order.OrderCoreService;
import com.hanghae.ecommerce.domain.payment.Payment;
import com.hanghae.ecommerce.domain.payment.PaymentCoreService;
import com.hanghae.ecommerce.domain.product.Product;
import com.hanghae.ecommerce.domain.product.ProductCoreService;
import com.hanghae.ecommerce.domain.user.User;
import com.hanghae.ecommerce.domain.user.UserCoreService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class OrderUseCase {

    private final UserCoreService userService;
    private final ProductCoreService productService;
    private final OrderCoreService orderService;
    private final PaymentCoreService paymentService;

    public OrderUseCase(UserCoreService userService,
                        ProductCoreService productService,
                        OrderCoreService orderService,
                        PaymentCoreService paymentService) {
        this.userService = userService;
        this.productService = productService;
        this.orderService = orderService;
        this.paymentService = paymentService;
    }

    @Transactional
    public OrderPaidResult order(Long userId, OrderRequest request) {
        User user = userService.getUser(userId);
        List<Product> products = productService.getProductsByIds(request.products());

        Order order = orderService.order(user, products, request);

        Payment payment = paymentService.pay(user, order, request);

        productService.decreaseStock(products, request);
        return OrderPaidResult.of(order, payment);
    }
}
