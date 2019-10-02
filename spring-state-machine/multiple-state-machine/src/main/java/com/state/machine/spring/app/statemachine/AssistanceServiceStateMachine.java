package com.state.machine.spring.app.statemachine;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;
import org.springframework.stereotype.Service;

import com.state.machine.spring.app.util.AssistanceEvents;
import com.state.machine.spring.app.util.AssistanceStates;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;


@Service
public class AssistanceServiceStateMachine extends AbstractServiceStateMachine<AssistanceStates, AssistanceEvents, String> {
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
    public static final String ASSISTANCE_CALL = "AssistanceCall";

    static final Map<String, StateMachine<AssistanceStates, AssistanceEvents>> machine = new HashMap<String, StateMachine<AssistanceStates, AssistanceEvents>>();

    public AssistanceServiceStateMachine(StateMachineRuntimePersister<AssistanceStates, AssistanceEvents, String> stateMachineRuntimePersister) {
        super(stateMachineRuntimePersister);
    }

    protected StateMachine<AssistanceStates, AssistanceEvents> createNewStateMachine(long timeout, String machineId) throws Exception {
        StateMachineBuilder.Builder<AssistanceStates, AssistanceEvents> builder = createBuilder(ASSISTANCE_CALL + machineId);

        builder.configureStates()
               .withStates()
               .initial(AssistanceStates.SUCCESSFUL)
               .states(EnumSet.allOf(AssistanceStates.class));

        builder.configureTransitions()
               .withExternal()
               .source(AssistanceStates.SUCCESSFUL)
               .target(AssistanceStates.WAITING_FOR_ACK)
               .event(AssistanceEvents.MESSAGE_SENT_EVENT)
               .and()
               .withExternal()
               .source(AssistanceStates.WAITING_FOR_ACK)
               .target(AssistanceStates.SUCCESSFUL)
               .event(AssistanceEvents.POSITIVE_EOS_ACK_RECEVIED_EVENT)
               .and()
               .withInternal()
               .source(AssistanceStates.WAITING_FOR_ACK)
               .event(AssistanceEvents.POSITIVE_REQ_ACK_RECEVIED_EVENT)
               .and()
               .withExternal()
               .source(AssistanceStates.WAITING_FOR_ACK)
               .target(AssistanceStates.FAILED)
               .event(AssistanceEvents.FAILED_TO_DECODE_ETA_EVENT)
               .and()
               .withExternal()
               .source(AssistanceStates.WAITING_FOR_ACK)
               .target(AssistanceStates.TIMED_OUT)
               .timerOnce(timeout);
        return builder.build();
    }

    @Override
    protected String getServiceType() {
        return ASSISTANCE_CALL;
    }
}


