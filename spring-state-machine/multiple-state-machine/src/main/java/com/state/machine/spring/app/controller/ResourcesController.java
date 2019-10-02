package com.state.machine.spring.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.state.machine.spring.app.repository.DynamicServiceRepository;
import com.state.machine.spring.app.service.DynamicService;
import com.state.machine.spring.app.service.DynamicServiceSpecification;

@RestController
@RequestMapping("/resources")
public class ResourcesController {

    @Autowired
    private DynamicServiceRepository dynamicServiceRepository;

    public ResourcesController(final DynamicServiceRepository dynamicServiceRepository) {
        this.dynamicServiceRepository = dynamicServiceRepository;
    }

    @GetMapping("/{service}/dynamic-service-specification")
    public DynamicServiceSpecification getDynamicServiceSpecification(@PathVariable("service") DynamicService dynamicService) {
        return dynamicService.getDynamicServiceSpecification();
    }

    @GetMapping("/available-dynamic-services")
    public  List<DynamicServiceSpecification> getAvailableDynamicServices() {
        return dynamicServiceRepository.getAvailableDynamicServices();
    }

}
