package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/employees")
@Slf4j
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;

	/**
	 * MongoDB,Redis의 경우 reactive 지원(Event loop가 이벤트를 감지하여 리턴)
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public Mono<Employee> get(@PathVariable String id) {
		log.info("get start");
		Mono<Employee> mono = employeeService.get(id);
		mono.subscribe(e -> log.info("mono : " + e.toString()));

		Mono<Employee> mono2 = employeeService.get(id);
		mono2.subscribe(e -> log.info("mono2 : " + e.toString()));

		log.info("get end");
		return employeeService.get(id);
	}

	@PostMapping
	public Mono<Boolean> create() {
		Employee employee = new Employee("test", "leejeongwha", "경기도 성남시 분당구");
		return employeeService.create(employee);
	}
}
