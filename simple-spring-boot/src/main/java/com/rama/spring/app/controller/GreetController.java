package com.rama.spring.app.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class GreetController {
	@Autowired
	RestTemplate restTemplate;
	@RequestMapping(value = "/greet", method = RequestMethod.GET)
	@ResponseBody
	public String currentUserName() {
		return "Hello Rama";
	}
	
	@RequestMapping(value = "/greetname", method = RequestMethod.GET)
	@ResponseBody
	public String userName(@RequestParam String name /*@PathParam("name") String name*/) {
		return "Hello "+name;
	}
	
	
}

