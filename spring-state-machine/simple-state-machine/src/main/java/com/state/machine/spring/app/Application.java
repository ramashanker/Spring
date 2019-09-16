package com.state.machine.spring.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;

import com.state.machine.spring.app.util.Events;
import com.state.machine.spring.app.util.States;

@SpringBootApplication
public class Application implements CommandLineRunner {
	@Autowired
	private StateMachine<States, Events> stateMachine;
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

	@Override
	public void run(String... args) throws Exception {
		stateMachine.sendEvent(Events.E1);
	    stateMachine.sendEvent(Events.E2);
		
	}
}