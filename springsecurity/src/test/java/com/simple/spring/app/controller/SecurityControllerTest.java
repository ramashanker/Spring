package com.simple.spring.app.controller;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.junit.Test;

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

@RunWith(SpringRunner.class)
@WebMvcTest(SecurityController.class)
@ActiveProfiles("test")
public class SecurityControllerTest {
	
	@Value("${client.auth.usename}")
	String username;
	
	@Value("${client.auth.password}")
	String password;
	
	@Value("${user.url}")
	String user_url;
	
	@Value("${admin.url}")
	String admin_url;
	
	@Autowired
    private MockMvc mockMvc;
	
	@Test
	public void userInvalidAuthorization() throws Exception {
		String username = "invalid";
		String password = "invalid";
		ResultActions responseEntity = processApiRequest(user_url, HttpMethod.GET, username, password);
		responseEntity.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void userValidAuthorization() throws Exception {
		
		ResultActions responseEntity = processApiRequest(user_url, HttpMethod.GET, username, password);
		responseEntity.andExpect(status().isOk());
	}
	
	@Test
	public void adminInvalidAuthorization() throws Exception {
		String username = "invalid";
		String password = "invalid";
		ResultActions responseEntity = processApiRequest(user_url, HttpMethod.GET, username, password);
		responseEntity.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void adminValidAuthorization() throws Exception {
		
		ResultActions responseEntity = processApiRequest(admin_url, HttpMethod.GET, username, password);
		responseEntity.andExpect(status().isOk());
	}
	
	private ResultActions processApiRequest(String url, HttpMethod methodType, String username, String password) {
        ResultActions response = null;
        try {
            switch (methodType) {
                case GET:
                    response = mockMvc.perform(get(url).with(httpBasic(username, password)));
                    break;
                case POST:
                    response = mockMvc.perform(post(url).with(httpBasic(username, password)));
                    break;
                default:
                    fail("Method Not supported");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        return response;
    }
}
