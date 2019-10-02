package com.state.machine.spring.app.service;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

import org.springframework.stereotype.Component;


@Getter
@Component
public class DynamicServiceSpecification {
    private String name;
    private List <String > urls;
    
    public DynamicServiceSpecification() {

    }

    @JsonCreator
    public DynamicServiceSpecification(@JsonProperty(value = "name", required = true) String name,@JsonProperty(value = "urls", required = true) List <String > urls) {
        this.name = name;
        this.urls = urls;
       
    }
}
