package com.hanghae.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI openApi() {
		return new OpenAPI()
			.components(new Components())
			.info(info());
	}

	private Info info() {
		return new Info()
			.title("E-Commerce 서비스 API Docs")
			.description("이커머스 서버 구축")
			.version("v1.0.0");
	}
}
