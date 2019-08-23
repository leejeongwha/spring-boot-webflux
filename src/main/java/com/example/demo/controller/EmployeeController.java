package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
		Mono<Employee> mono = employeeService.findById(id);
		mono.subscribe(e -> log.info("mono : " + e.toString()));

		Mono<Employee> mono2 = employeeService.findById(id);
		mono2.subscribe(e -> log.info("mono2 : " + e.toString()));

		log.info("get end");
		return employeeService.findById(id);
	}

	@GetMapping
	public ResponseEntity<Mono<List<Employee>>> getAll() {
		Mono<List<Employee>> employees = employeeService.findByPattern("*");
		return new ResponseEntity<>(employees, HttpStatus.OK);
	}

	@PostMapping
	public Mono<Boolean> create(Employee employee) {
		return employeeService.save(employee);
	}

	@PutMapping("/{id}")
	public Mono<Boolean> update(@PathVariable String id, Employee employee) {
		employee.setId(id);
		return employeeService.update(employee);
	}

	@DeleteMapping("/{id}")
	public Mono<Long> delete(@PathVariable String id) {
		return employeeService.delete(id);
	}
}
