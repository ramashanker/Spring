package com.simple.spring.app.controller;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.simple.spring.app.util.SecConstatnts;

@RestController
public class SecurityController {

	@RequestMapping(value = SecConstatnts.USER_URL, method = RequestMethod.GET)
    @ResponseBody
    public String currentUserName(Authentication authentication) {
        return authentication.getName();
    }
   
    @RequestMapping(value = SecConstatnts.ADMIN_URL, method = RequestMethod.GET)
    @ResponseBody
    public String currentUserName(Principal principal) {
        return principal.getName();
    }
}
