package com.hanghae.ecommerce.common;

import org.springframework.stereotype.Service;

import com.hanghae.ecommerce.domain.order.Order;
import com.hanghae.ecommerce.domain.payment.Payment;

import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

@Service
@Slf4j
public class DataPlatformSendService {
	private final OkHttpClient client = new OkHttpClient();

	public void send(Order order, Payment payment) {
		String url = "https://mockapi.com";

		RequestBody body = RequestBody.create(
			order.toString() + payment.toString(),
			MediaType.get("application/json; charset=utf-8")
		);

		Request request = new Request.Builder()
			.url(url)
			.post(body)
			.build();

		// 데이터 전송
	}
}
