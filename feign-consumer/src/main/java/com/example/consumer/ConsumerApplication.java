package com.example.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

// 啟動Feign功能
@EnableFeignClients
@SpringBootApplication
public class ConsumerApplication {

	@Bean
	public ReflectionDemo reflectionDemo() {
		return new ReflectionDemo();
	}

	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}
}
