package com.state.machine.spring.app.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.config.StateMachineBuilder;

import com.state.machine.spring.app.repository.DynamicServiceRepository;


public class DynamicService {

    private static final Logger logger = LoggerFactory.getLogger(DynamicService.class);
    private DynamicServiceRepository dynamicServiceRepository;
 
    private DynamicServiceSpecification dynamicServiceSpecification;

    private StateMachineBuilder.Builder<String, String> builder = StateMachineBuilder.builder();

    public DynamicService(DynamicServiceRepository dynamicServiceRepository, DynamicServiceSpecification dynamicServiceSpecification) {
        this.dynamicServiceRepository = dynamicServiceRepository;
        this.dynamicServiceSpecification = dynamicServiceSpecification;

  
    }

    public DynamicServiceSpecification getDynamicServiceSpecification() {
        return dynamicServiceSpecification;
    }

   
}
