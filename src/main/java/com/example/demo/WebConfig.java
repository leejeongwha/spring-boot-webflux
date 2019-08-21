package com.example.demo;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.demo.handler.HelloWorldHandler;

@Configuration
public class WebConfig implements WebFluxConfigurer {
	/**
	 * 요청을 handler로 라우팅
	 * 
	 * @param handler
	 * @return
	 */
	@Bean
	public RouterFunction<ServerResponse> routes(HelloWorldHandler handler) {
		return RouterFunctions.route(GET("/hello"), handler::helloWorld);
	}
}
