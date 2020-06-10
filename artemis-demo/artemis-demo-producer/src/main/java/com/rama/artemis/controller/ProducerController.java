package com.rama.artemis.controller;

import com.rama.artemis.producer.ArtemisProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {
    @Autowired
    ArtemisProducer producer;

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public String produce(@RequestParam("msg")String msg){
        producer.send(msg);
        return "Done";
    }
}
