package com.state.machine.spring.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

import com.state.machine.spring.app.statemachine.AssistanceServiceStateMachine;
import com.state.machine.spring.app.statemachine.ProvisionServiceStateMachine;
import com.state.machine.spring.app.util.AssistanceEvents;
import com.state.machine.spring.app.util.AssistanceStates;
import com.state.machine.spring.app.util.ProvisionEvents;
import com.state.machine.spring.app.util.ProvisionStates;

@Service
public class StateMachineService {
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	@Value("${assistanceservice.timeout:20000}")
    private long assistanceCallServiceTimeout;

    @Value("${provisionservice.timeout:30000}")
    private long provisionServiceTimeout;
    
	private final AssistanceServiceStateMachine assistanceServiceStateMachine;
    private final ProvisionServiceStateMachine provisionServiceStateMachine;
    
    @Autowired
    public StateMachineService(AssistanceServiceStateMachine assistanceServiceStateMachine,
                          
                          ProvisionServiceStateMachine provisionServiceStateMachine
                       ) {
        this.assistanceServiceStateMachine = assistanceServiceStateMachine;
       
        this.provisionServiceStateMachine = provisionServiceStateMachine;
     
       
    }
    public void initiateStateMachine() throws Exception {
    	 StateMachine<AssistanceStates, AssistanceEvents> assistanceCallStateMachine = assistanceServiceStateMachine.getStateMachine(
    			 assistanceCallServiceTimeout,
                 "1234");
         assistanceCallStateMachine.start();
         LOGGER.info("Assistance Call:MESSAGE_SENT");
         assistanceCallStateMachine.sendEvent(AssistanceEvents.MESSAGE_SENT_EVENT);
         StateMachine<ProvisionStates, ProvisionEvents> provisionCallStateMachine = provisionServiceStateMachine.getStateMachine(provisionServiceTimeout,
                 "1235");
         provisionCallStateMachine.start();
         LOGGER.info("Provision Call:SENT_CONFIGURATION_REQUEST_MESSAGE");
         provisionCallStateMachine.sendEvent(ProvisionEvents.SENT_CONFIGURATION_MESSAGE_EVENT);
        
    }

}
