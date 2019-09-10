package com.state.machine.spring.app.statemachine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;
import org.springframework.statemachine.state.State;

public abstract class AbstractServiceStateMachine<S, E, T extends String> {
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private StateMachineRuntimePersister<S, E, String> stateMachineRuntimePersister;

    public AbstractServiceStateMachine(StateMachineRuntimePersister<S, E, String> stateMachineRuntimePersister) {
        this.stateMachineRuntimePersister = stateMachineRuntimePersister;
    }

    public StateMachine<S, E> getStateMachine(long timeout, String machineId) throws Exception {
        return restoreStateMachine(createNewStateMachine(timeout, machineId),
                                   stateMachineRuntimePersister.read(getServiceType() + machineId));
    }

    protected StateMachine<S, E> restoreStateMachine(StateMachine<S, E> stateMachine, final StateMachineContext<S, E> stateMachineContext) {
        if (stateMachineContext == null) {
            return stateMachine;
        }
        stateMachine.stop();
        stateMachine.getStateMachineAccessor()
                    .doWithRegion(function -> function.resetStateMachine(stateMachineContext));
        return stateMachine;
    }

    public StateMachine<S, E> recreateStateMachine(long timeout, String machineId) throws Exception {
        return createNewStateMachine(timeout, machineId);
    }

    protected StateMachineBuilder.Builder<S, E> createBuilder(String machineId) throws Exception {
        StateMachineBuilder.Builder<S, E> builder = StateMachineBuilder.builder();
        builder.configureConfiguration()
               .withConfiguration()
               .listener(createListener())
               .machineId(machineId);
        builder.configureConfiguration()
               .withPersistence()
               .runtimePersister(getStateMachineRuntimePersister());

        return builder;
    }

    protected abstract StateMachine<S, E> createNewStateMachine(long timeout, String machineId) throws Exception;

    protected abstract String getServiceType();

    protected StateMachineRuntimePersister<S, E, String> getStateMachineRuntimePersister() {
        return this.stateMachineRuntimePersister;
    }

    protected StateMachineListener<S, E> createListener() {
        return new StateMachineListenerAdapter<S, E>() {
            @Override
            public void eventNotAccepted(Message<E> event) {
                LOGGER.error("Event not accepted: {}", event.getPayload());
            }
            @Override
            public void stateChanged(State<S, E> from, State<S, E> to) {
            	if(from!=null )			
                 System.out.println("State change from: "+ from.getId() +" to: " + to.getId());
            	else
            	 System.out.println("State change to: " + to.getId());
            }
        };
    }
}


