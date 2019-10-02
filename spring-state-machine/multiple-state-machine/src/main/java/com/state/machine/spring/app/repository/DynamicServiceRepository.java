package com.state.machine.spring.app.repository;

import static com.state.machine.spring.app.exception.ExceptionHandling.handleException;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.state.machine.spring.app.service.DynamicServiceSpecification;

@Repository
public class DynamicServiceRepository {

    private List<DynamicServiceSpecification> dynamicServices = new ArrayList<DynamicServiceSpecification>();

    @PostConstruct
    public void postConstruct() {
        handleException("error loading existing dynamic services", () -> {
            ObjectMapper objectMapper = new ObjectMapper();

            for (Resource resource : new PathMatchingResourcePatternResolver().getResources("classpath:dynamicservices/*.*")) {
                addDynamicService(objectMapper.readValue(String.join("\n",
                                                                     IOUtils.readLines(resource.getInputStream(), StandardCharsets.UTF_8)),
                                                         DynamicServiceSpecification.class));
            }
        });
    }

    public void addDynamicService(DynamicServiceSpecification dynamicServiceSpecification) {
        dynamicServices.add(dynamicServiceSpecification);
    }


    public List<DynamicServiceSpecification> getAvailableDynamicServices() {
        return dynamicServices;
    }
}
