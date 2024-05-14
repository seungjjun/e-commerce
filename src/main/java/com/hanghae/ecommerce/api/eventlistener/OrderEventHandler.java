package com.hanghae.ecommerce.api.eventlistener;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.order.event.OrderCreatedEvent;
import com.hanghae.ecommerce.domain.payment.Payment;

import lombok.extern.log4j.Log4j2;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

@Component
@Log4j2
public class OrderEventHandler {
	private final OkHttpClient client = new OkHttpClient();

	@Async
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void orderEventHandler(OrderCreatedEvent event) {
		String url = "https://mockapi.com";

		Order order = event.order();
		Payment payment = event.payment();

		RequestBody body = RequestBody.create(
			order.toString() + payment.toString(),
			MediaType.get("application/json; charset=utf-8")
		);

		Request request = new Request.Builder()
			.url(url)
			.post(body)
			.build();

		try {
			client.newCall(request).execute();
		} catch (Exception e) {
			log.error("주문 정보 전송 실패, {}", e.getMessage());
		}
	}
}
