package com.example.demo.controller;

import java.util.Random;

import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.model.Employee;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequestMapping("api")
public class NonBlockingHttpCallController {
	WebClient client = WebClient.create();

	@GetMapping("test")
	public Mono<Employee> test() throws InterruptedException {
		Random random = new Random();
		Integer randomTime = random.nextInt(5);

		log.info("This controller takes {} seconds", randomTime);
		Thread.sleep(randomTime * 1000);

		return Mono.just(new Employee(Integer.toString(randomTime), "test", "test"));
	}

	/**
	 * 아래 2개의 Http 요청은 비동기적으로 발생(순차적 호출이 필요하지 않은 경우라면 좋음)
	 */
	@GetMapping("/nonBlocking")
	public void nonBlocking() {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		for (int i = 0; i < 2; i++) {
			Mono<Employee> bodyToMono = client.get().uri("http://localhost:8080/api/test").retrieve()
					.bodyToMono(Employee.class);

			bodyToMono.subscribe(employee -> {
				if (stopWatch.isRunning()) {
					stopWatch.stop();
				}
				log.info(employee.toString());
				log.info(stopWatch.prettyPrint());
				stopWatch.start();
			});
		}

		System.out.println("testWebClient end!");
		log.info(stopWatch.prettyPrint());
	}
}
