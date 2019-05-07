package com.rmit.engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.rmit")
@EnableMongoRepositories(basePackages = "com.rmit.main.library")
@EnableFeignClients(basePackages = "com.rmit")
public class RmitEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(RmitEngineApplication.class, args);
	}
}
