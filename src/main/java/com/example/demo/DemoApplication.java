package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.example.demo.model.Employee;

@SpringBootApplication
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	@Primary
	public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
		return new LettuceConnectionFactory("10.105.183.97", 6379);
	}

	@Bean
	public ReactiveRedisTemplate<String, Employee> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
		StringRedisSerializer keySerializer = new StringRedisSerializer();
		Jackson2JsonRedisSerializer<Employee> valueSerializer = new Jackson2JsonRedisSerializer<>(Employee.class);
		RedisSerializationContext.RedisSerializationContextBuilder<String, Employee> builder = RedisSerializationContext
				.newSerializationContext(keySerializer);
		RedisSerializationContext<String, Employee> context = builder.value(valueSerializer).build();

		return new ReactiveRedisTemplate<>(factory, context);
	}
}
