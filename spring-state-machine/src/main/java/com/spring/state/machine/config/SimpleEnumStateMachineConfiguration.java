package com.spring.state.machine.config;

import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;

import com.spring.state.machine.event.OrderEvents;
import com.spring.state.machine.event.OrderStates;

public class SimpleEnumStateMachineConfiguration
        extends EnumStateMachineConfigurerAdapter<OrderStates, OrderEvents> {

    
}
