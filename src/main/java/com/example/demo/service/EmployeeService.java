package com.example.demo.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.stereotype.Service;

import com.example.demo.model.Employee;

import reactor.core.publisher.Mono;

@Service
public class EmployeeService {
	@Autowired
	private ReactiveRedisTemplate<String, Employee> redisTemplate;

	private ReactiveValueOperations<String, Employee> reactiveValueOps;

	public Mono<Employee> get(String id) {
		return reactiveValueOps.get(id);
	}

	public Mono<Boolean> create(Employee employee) {
		return reactiveValueOps.set(employee.getId(), employee);
	}

	@PostConstruct
	public void setup() {
		reactiveValueOps = redisTemplate.opsForValue();
	}
}
