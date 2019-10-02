package com.state.machine.spring.app.statemachine;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;
import org.springframework.statemachine.state.State;

import com.state.machine.spring.app.util.StateMachineData;
import com.state.machine.spring.app.websocket.WebSocketService;

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

	protected StateMachine<S, E> restoreStateMachine(StateMachine<S, E> stateMachine,
			final StateMachineContext<S, E> stateMachineContext) {
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
       // List<StateMachineData> statesdata= new ArrayList<StateMachineData>();
		builder.configureConfiguration().withConfiguration().listener(createListener()).machineId(machineId);
		builder.configureConfiguration().withPersistence().runtimePersister(getStateMachineRuntimePersister());

		return builder;
	}

	protected abstract StateMachine<S, E> createNewStateMachine(long timeout, String machineId) throws Exception;

	protected abstract String getServiceType();

	protected StateMachineRuntimePersister<S, E, String> getStateMachineRuntimePersister() {
		return this.stateMachineRuntimePersister;
	}

	protected StateMachineListener<S, E> createListener() {
		List<StateMachineData> statesdata= new ArrayList<StateMachineData>();
		return new StateMachineListenerAdapter<S, E>() {
			String event = null;
			@Override
			public void stateContext(StateContext<S, E> stateContext) {			
				if (stateContext.getMessage() != null) {
					event = stateContext.getMessage().getPayload().toString();				
				}

			}
			@Override
			public void eventNotAccepted(Message<E> event) {
				LOGGER.error("Event not accepted: {}", event.getPayload());
			}

			@Override
			public void stateChanged(State<S, E> from, State<S, E> to) {
				if (from != null) {
					LOGGER.info("State change from: " + from.getId() + " to: " + to.getId());
					StateMachineData statemachineData = new StateMachineData();
					statemachineData.setSource(from.getId().toString());
					statemachineData.setTarget(to.getId().toString());
					statemachineData.setEvent(event);
					statesdata.add(statemachineData);
					WebSocketService.sendObject(statesdata, "/topic/state");
				}

				else {
					LOGGER.info("State machine initialised in state " + to.getId());
				}
				
			}

		};
	}

}
