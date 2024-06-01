package com.hanghae.ecommerce.domain.product;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.hanghae.ecommerce.Fixtures;
import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.order.OrderItem;
import com.hanghae.ecommerce.domain.order.OrderReader;
import com.hanghae.ecommerce.domain.order.OrderUpdater;
import com.hanghae.ecommerce.domain.product.event.StockEventPublisher;
import com.hanghae.ecommerce.storage.order.OrderStatus;

class StockServiceTest {
	private StockService stockService;
	private StockReader stockReader;
	private StockValidator stockValidator;
	private StockUpdator stockUpdator;
	private OrderReader orderReader;
	private OrderUpdater orderUpdater;
	private StockEventPublisher eventPublisher;

	@BeforeEach
	void setUp() {
		stockReader = mock(StockReader.class);
		stockValidator = mock(StockValidator.class);
		stockUpdator = mock(StockUpdator.class);
		orderReader = mock(OrderReader.class);
		orderUpdater = mock(OrderUpdater.class);
		eventPublisher = mock(StockEventPublisher.class);

		stockService =
			new StockService(stockReader, stockValidator, stockUpdator, orderReader, orderUpdater, eventPublisher);
	}

	@Test
	@DisplayName("상품 재고를 감소시킨다.")
	void decrease_product_stock_quantity() {
		// Given
		Product product = Fixtures.product("후드티");
		Stock stock = Fixtures.stock(product.id());

		given(stockReader.readByProductId(any())).willReturn(stock);

		OrderItem item =
			new OrderItem(1L, 1L, product.id(), product.name(), product.price(), product.orderTotalPrice(3L), 3L,
				"CREATED");

		// When
		stockService.decreaseProductStock(item);

		// Then
		verify(stockValidator, atLeastOnce()).checkProductStockQuantityForOrder(any(), any());
		verify(stockUpdator, atLeastOnce()).decreaseStock(any(), any());
	}

	@Test
	@DisplayName("주문이 실패되었을 때, 상품 재고가 감소된 상품의 재고를 다시 증가시킨다.")
	void when_order_failed_then_compensate_stock_quantity() {
		// Given
		Order order = Fixtures.order(OrderStatus.READY);
		Stock stock = Fixtures.stock(order.items().get(1).productId());

		given(orderReader.read(any())).willReturn(order);
		given(stockReader.readByProductId(any())).willReturn(stock);

		// When
		stockService.compensateOrderStock(order);

		// Then
		verify(stockUpdator, atLeastOnce()).increaseStock(any(), any());
	}
}
