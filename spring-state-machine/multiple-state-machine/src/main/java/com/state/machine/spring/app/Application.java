package com.state.machine.spring.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.state.machine" })
public class Application{
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}