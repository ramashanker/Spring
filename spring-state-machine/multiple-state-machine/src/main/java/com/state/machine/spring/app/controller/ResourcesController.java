package com.state.machine.spring.app.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.state.machine.spring.app.repository.DynamicServiceRepository;
import com.state.machine.spring.app.service.DynamicService;
import com.state.machine.spring.app.service.DynamicServiceSpecification;

import java.util.Set;

@RestController
@RequestMapping("/resources")
public class ResourcesController {

    @Autowired
    private DynamicServiceRepository dynamicServiceRepository;

    public ResourcesController(final DynamicServiceRepository dynamicServiceRepository) {
        this.dynamicServiceRepository = dynamicServiceRepository;
    }

    @PostMapping("")
    public void addDynamicService(@RequestBody DynamicServiceSpecification dynamicServiceSpecification) {
        dynamicServiceRepository.addDynamicService(dynamicServiceSpecification);
    }

    @GetMapping("/{service}/dynamic-service-specification")
    public DynamicServiceSpecification getDynamicServiceSpecification(@PathVariable("service") DynamicService dynamicService) {
        return dynamicService.getDynamicServiceSpecification();
    }

    @GetMapping("/available-dynamic-services")
    public Set<String> getAvailableDynamicServices() {
        return dynamicServiceRepository.getAvailableDynamicServices();
    }

}
