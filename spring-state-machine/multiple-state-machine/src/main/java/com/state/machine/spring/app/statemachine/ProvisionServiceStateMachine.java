package com.state.machine.spring.app.statemachine;



import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;
import org.springframework.stereotype.Service;

import com.state.machine.spring.app.util.AssistanceEvents;
import com.state.machine.spring.app.util.AssistanceStates;
import com.state.machine.spring.app.util.ProvisionEvents;
import com.state.machine.spring.app.util.ProvisionStates;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;


@Service
public class ProvisionServiceStateMachine extends AbstractServiceStateMachine<ProvisionStates, ProvisionEvents, String> {

    public static final String PROVISION_CALL = "ProvisionCall";
    static final Map<String, StateMachine<AssistanceStates, AssistanceEvents>> machine = new HashMap<String, StateMachine<AssistanceStates, AssistanceEvents>>();


    public ProvisionServiceStateMachine(StateMachineRuntimePersister<ProvisionStates, ProvisionEvents, String> stateMachineRuntimePersister) {
        super(stateMachineRuntimePersister);
    }

    protected StateMachine<ProvisionStates, ProvisionEvents> createNewStateMachine(long timeout, String machineId) throws Exception {
        StateMachineBuilder.Builder<ProvisionStates, ProvisionEvents> builder = createBuilder(PROVISION_CALL + machineId);

        builder.configureStates()
               .withStates()
               .initial(ProvisionStates.INITIATE)
               .states(EnumSet.allOf(ProvisionStates.class));

        builder.configureTransitions()
               .withExternal()
               .source(ProvisionStates.INITIATE)
               .target(ProvisionStates.WAITING_FOR_CONFIGURATION_DATA_MESSAGE)
               .event(ProvisionEvents.SENT_CONFIGURATION_REQUEST_MESSAGE)
               .and()
               .withExternal()
               .source(ProvisionStates.WAITING_FOR_CONFIGURATION_DATA_MESSAGE)
               .target(ProvisionStates.SUCCESSFUL_PROVISION)
               .event(ProvisionEvents.RECEIVED_CONFIGURATION_DATA_MESSAGE)
               .and()
               .withExternal()
               .source(ProvisionStates.WAITING_FOR_CONFIGURATION_DATA_MESSAGE)
               .target(ProvisionStates.TIMED_OUT)
               .timerOnce(timeout);
        return builder.build();
    }

    @Override
    protected String getServiceType() {
        return PROVISION_CALL;
    }
}
