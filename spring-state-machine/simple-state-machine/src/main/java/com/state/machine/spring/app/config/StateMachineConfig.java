package com.state.machine.spring.app.config;

import java.util.EnumSet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import com.state.machine.spring.app.util.Events;
import com.state.machine.spring.app.util.States;

/*
@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
        config.withConfiguration()
              .autoStartup(true)
              .listener(listener());
    }

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        states.withStates()
              .initial(States.SUCCESSFUL)
              .states(EnumSet.allOf(States.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions.withExternal()
                   .source(States.SUCCESSFUL)
                   .target(States.WAITING_FOR_CONFIGURATION_DATA_MESSAGE)
                   .event(Events.SENT_CONFIGURATION_REQUEST_MESSAGE)
                   .and()
                   .withExternal()
                   .source(States.WAITING_FOR_CONFIGURATION_DATA_MESSAGE)
                   .target(States.SUCCESSFUL)
                   .event(Events.RECEIVED_CONFIGURATION_DATA_MESSAGE)
                   .and()
                   .withExternal()
                   .source(States.WAITING_FOR_CONFIGURATION_DATA_MESSAGE)
                   .target(States.TIMED_OUT)
                   .timerOnce(30000);
    }

    @Bean
    public StateMachineListener<States, Events> listener() {
        return new StateMachineListenerAdapter<States, Events>() {
            @Override
            public void stateChanged(State<States, Events> from, State<States, Events> to) {
                System.out.println("State change to " + to.getId());
            }
        };
    }
}*/
