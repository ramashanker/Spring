package com.state.machine.spring.app;

import com.state.machine.spring.app.config.AssistanceStateMachine;
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
		//stateMachine.sendEvent(Events.SENT_CONFIGURATION_REQUEST_MESSAGE);
		//Thread.sleep(25000);
	    //stateMachine.sendEvent(Events.RECEIVED_CONFIGURATION_DATA_MESSAGE);
		stateMachine.sendEvent(Events.MESSAGE_SENT);
		Thread.sleep(2000);
		stateMachine.sendEvent(Events.POSITIVE_REQ_ACK_RECEVIED);
		Thread.sleep(2000);
		stateMachine.sendEvent(Events.POSITIVE_EOS_ACK_RECEVIED);
	}
}