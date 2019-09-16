package com.state.machine.spring.app.config;

import com.state.machine.spring.app.websocket.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.file.tail.ApacheCommonsFileTailingMessageProducer;

import java.io.File;

@Configuration
public class StreamedApplicationLogConfig {

    @Autowired
    private WebSocketService webSocketService;

    @Value("${logging.file}")
    private String logFilePath;

    public MessageProducerSupport fileContentProducer() {
        ApacheCommonsFileTailingMessageProducer tailFileProducer = new ApacheCommonsFileTailingMessageProducer();
        tailFileProducer.setFile(new File(logFilePath));

        return tailFileProducer;
    }

    @Bean
    public IntegrationFlow tailFilesFlow() {
        return IntegrationFlows.from(this.fileContentProducer())
                               .handle("webSocketService", "sendLogRow")
                               .get();
    }
}