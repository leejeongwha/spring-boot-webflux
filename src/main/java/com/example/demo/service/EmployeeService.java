package com.example.demo.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.stereotype.Service;

import com.example.demo.model.Employee;

import reactor.core.publisher.Mono;

@Service
public class EmployeeService {
	private static final String REDIS_PREFIX_EMPLOYEES = "test:employees";
	private static final String REDIS_KEYS_SEPARATOR = ":";

	@Autowired
	private ReactiveRedisTemplate<String, Employee> redisTemplate;
	private ReactiveValueOperations<String, Employee> reactiveValueOps;

	/**
	 * * : 모든 문자 매치(match) 
	 * ? : 1개 문자 매치(match) 
	 * [alphabet] : 대괄호 안에 있는 문자 매치(match)
	 * 
	 * @param pattern
	 * @return
	 */
	public Mono<List<Employee>> findByPattern(String pattern) {
		List<String> keyList = redisTemplate.keys(REDIS_PREFIX_EMPLOYEES + pattern).collectList().block();

		return reactiveValueOps.multiGet(keyList);
	}

	public Mono<Employee> findById(String employeeId) {
		return reactiveValueOps.get(getRedisKey(employeeId));
	}

	public Mono<Boolean> save(Employee employee) {
		return reactiveValueOps.set(getRedisKey(employee.getId()), employee);
	}

	public Mono<Boolean> update(Employee employee) {
		Employee find = reactiveValueOps.get(getRedisKey(employee.getId())).block();
		return reactiveValueOps.set(getRedisKey(find.getId()), employee);
	}

	public Mono<Long> delete(String employeeId) {
		return redisTemplate.delete(getRedisKey(employeeId));
	}

	private String getRedisKey(String employeeId) {
		return REDIS_PREFIX_EMPLOYEES + REDIS_KEYS_SEPARATOR + employeeId;
	}

	@PostConstruct
	public void setup() {
		reactiveValueOps = redisTemplate.opsForValue();
	}
}
