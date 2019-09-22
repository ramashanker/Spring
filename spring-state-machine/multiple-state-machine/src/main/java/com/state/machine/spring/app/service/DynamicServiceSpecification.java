package com.state.machine.spring.app.service;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.stereotype.Component;


@Getter
@Component
public class DynamicServiceSpecification {
    private String name;
    

    public DynamicServiceSpecification() {

    }

    @JsonCreator
    public DynamicServiceSpecification(@JsonProperty(value = "name", required = true) String name) {
        this.name = name;
       
    }
}
