package com.state.machine.spring.app.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.data.jpa.JpaPersistingStateMachineInterceptor;
import org.springframework.statemachine.data.jpa.JpaStateMachineRepository;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;

import com.state.machine.spring.app.util.AssistanceEvents;
import com.state.machine.spring.app.util.AssistanceStates;
import com.state.machine.spring.app.util.ProvisionEvents;
import com.state.machine.spring.app.util.ProvisionStates;

@Configuration
public class StateMachineConfig {

	 @Bean
	    public StateMachineRuntimePersister<AssistanceStates, AssistanceEvents, String> assistanceStateMachineRuntimePersister(
	            JpaStateMachineRepository jpaStateMachineRepository) {
	        return new JpaPersistingStateMachineInterceptor<>(jpaStateMachineRepository);
	    }
	 
	 @Bean
	    public StateMachineRuntimePersister<ProvisionStates,ProvisionEvents, String> provisionStateMachineRuntimePersister(
	            JpaStateMachineRepository jpaStateMachineRepository) {
	        return new JpaPersistingStateMachineInterceptor<>(jpaStateMachineRepository);
	    }
}
