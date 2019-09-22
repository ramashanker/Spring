package com.state.machine.spring.app.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.*;

import com.state.machine.spring.app.statemachine.AssistanceServiceStateMachine;
import com.state.machine.spring.app.statemachine.ProvisionServiceStateMachine;
import com.state.machine.spring.app.util.AssistanceEvents;
import com.state.machine.spring.app.util.AssistanceStates;
import com.state.machine.spring.app.util.Constants;
import com.state.machine.spring.app.util.ProvisionEvents;
import com.state.machine.spring.app.util.ProvisionStates;


@Api(produces = "application/json", description = "State Machine Controller")
@RestController
@RequestMapping("/state-machine")
public class StateMachineController {
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	@Value("${assistanceservice.timeout:20000}")
    private long assistanceCallServiceTimeout;

    @Value("${provisionservice.timeout:30000}")
    private long provisionServiceTimeout;
    
    private final AssistanceServiceStateMachine assistanceServiceStateMachine;
    private final ProvisionServiceStateMachine provisionServiceStateMachine;
    
    @Autowired
    public StateMachineController(AssistanceServiceStateMachine assistanceServiceStateMachine,
                          
                          ProvisionServiceStateMachine provisionServiceStateMachine
                       ) {
        this.assistanceServiceStateMachine = assistanceServiceStateMachine;
       
        this.provisionServiceStateMachine = provisionServiceStateMachine;
     
       
    }
    @ApiOperation(value = "Initialize an Assistance Service")
    @PostMapping(Constants.ASSIST_URL)
    public String startAssistancee() throws Exception {
		 StateMachine<AssistanceStates, AssistanceEvents> assistanceCallStateMachine = assistanceServiceStateMachine.getStateMachine(
    			 assistanceCallServiceTimeout,
                 "1234");
         assistanceCallStateMachine.start();
         LOGGER.info("Assistance Call:MESSAGE_SENT");
         assistanceCallStateMachine.sendEvent(AssistanceEvents.MESSAGE_SENT);  
         Thread.sleep(10000);
         assistanceCallStateMachine.sendEvent(AssistanceEvents.POSITIVE_EOS_ACK_RECEVIED);  
         return assistanceCallStateMachine.getState().getId().name();
    }

    @ApiOperation(value = "Initialize an Provision Service")
    @PostMapping(Constants.PROVISION_URL)
    public String startProvision() throws Exception {
    	StateMachine<ProvisionStates, ProvisionEvents> provisionCallStateMachine = provisionServiceStateMachine.getStateMachine(provisionServiceTimeout,
                "1235");
    	provisionCallStateMachine.start();
        LOGGER.info("Provision Call:SENT_CONFIGURATION_REQUEST_MESSAGE");
        provisionCallStateMachine.sendEvent(ProvisionEvents.SENT_CONFIGURATION_REQUEST_MESSAGE);
        return provisionCallStateMachine.getState().getId().name();
    }
}
