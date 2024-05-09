package com.hanghae.ecommerce.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae.ecommerce.domain.product.Product;

@Configuration
public class RedissonConfig {
	@Value("${spring.data.redis.host}")
	private String redisHost;

	@Value("${spring.data.redis.port}")
	private int redisPort;

	private static final String REDISSON_HOST_PREFIX = "redis://";

	@Bean
	public RedissonClient redissonClient() {
		RedissonClient redisson;
		Config config = new Config();
		config.useSingleServer().setAddress(REDISSON_HOST_PREFIX + redisHost + ":" + redisPort);
		redisson = Redisson.create(config);
		return redisson;
	}

	@Bean
	public RedisTemplate<String, Product> redisTemplate(RedisConnectionFactory connectionFactory, ObjectMapper mapper) {
		RedisTemplate<String, Product> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);

		mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator(),
			ObjectMapper.DefaultTyping.NON_FINAL,
			JsonTypeInfo.As.PROPERTY);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		Jackson2JsonRedisSerializer<Product> serializer = new Jackson2JsonRedisSerializer<>(mapper, Product.class);

		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(serializer);
		template.afterPropertiesSet();
		return template;
	}
}
