package com.rama.artemis.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.UncategorizedJmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
public class ArtemisProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ArtemisProducer.class);
    @Autowired
    JmsTemplate jmsTemplate;

    @Value("${jms.queue.destination}")
    String destinationQueue;
    @Retryable(
            value = {UncategorizedJmsException.class},
            maxAttempts = 10,
            backoff = @Backoff(random = true, delay = 1000, maxDelay = 8000,multiplier = 2)
    )
    public void send(String msg) {
            LOGGER.info("Sending Data:");
            jmsTemplate.convertAndSend(destinationQueue, msg);
            LOGGER.info("Data Sent:");
    }
}

