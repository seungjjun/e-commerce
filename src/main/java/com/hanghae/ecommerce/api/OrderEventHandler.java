package com.hanghae.ecommerce.api;

import com.hanghae.ecommerce.api.dto.OrderEventForStatistics;
import lombok.extern.log4j.Log4j2;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class OrderEventHandler {
    private final OkHttpClient client = new OkHttpClient();

    @EventListener
    public void orderEventHandler(OrderEventForStatistics event) {
        String url = "https://mockapi.com";

        RequestBody body = RequestBody.create(event.toString(), MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try {
            client.newCall(request).execute();
        } catch (Exception e) {
            log.error("API 요청 실패, {}", e.getMessage());
        }
    }
}
