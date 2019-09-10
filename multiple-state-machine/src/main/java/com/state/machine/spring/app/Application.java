package com.state.machine.spring.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.state.machine.spring.app.service.StateMachineService;

@SpringBootApplication
public class Application implements CommandLineRunner {
	
	@Autowired
	private  StateMachineService stateMachineService;

	
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

	@Override
	public void run(String... args) throws Exception {
		
		stateMachineService.initiateStateMachine();
	}
}