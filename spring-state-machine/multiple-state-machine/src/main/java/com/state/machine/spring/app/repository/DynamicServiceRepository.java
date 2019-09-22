package com.state.machine.spring.app.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.state.machine.spring.app.service.DynamicService;
import com.state.machine.spring.app.service.DynamicServiceSpecification;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import static com.state.machine.spring.app.exception.ExceptionHandling.handleException;

@Repository
public class DynamicServiceRepository {

    private Map<String, DynamicService> dynamicServices = new HashMap<>();

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
        DynamicService dynamicService = new DynamicService(this, dynamicServiceSpecification);

        dynamicServices.put(dynamicServiceSpecification.getName(), dynamicService);
    }

    public DynamicService getDynamicService(String name) {
        return dynamicServices.get(name);
    }

    public Set<String> getAvailableDynamicServices() {
        return dynamicServices.keySet();
    }
}
