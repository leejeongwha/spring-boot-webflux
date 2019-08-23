package com.example.demo.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.demo.model.Employee;

import reactor.core.publisher.Mono;

@Component
public class HelloWorldHandler {
	public Mono<ServerResponse> helloWorld(ServerRequest request) {
		Mono<Employee> helloworldMono = Mono.just(new Employee("1", "leejeongwha", "korea"));
		return ServerResponse.ok().body(helloworldMono, Employee.class);
	}
}
