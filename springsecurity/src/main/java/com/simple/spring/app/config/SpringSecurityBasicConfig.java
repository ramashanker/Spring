package com.simple.spring.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.simple.spring.app.util.SecConstatnts;

@Configuration
@EnableWebSecurity
public class SpringSecurityBasicConfig extends WebSecurityConfigurerAdapter {
	@Value("${spring.security.user.name1:}")
	private String username1;
	@Value("${spring.security.user.name2:}")
	private String username2;

	@Value("${spring.security.user.password:}")
	private String password;
	
	@Value("${spring.security.admin.name:}")
	private String admin_name;

	@Value("${spring.security.admin.password:}")
	private String admin_password;
	
	@Value("${spring.security.user.role:}")
	private String USER_ROLE;
	
	@Value("${spring.security.admin.role:}")
	private String ADMIN_ROLE;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().and().authorizeRequests().antMatchers(SecConstatnts.SWAGGER).permitAll()
				.antMatchers(HttpMethod.GET, SecConstatnts.ADMIN_URL).hasAnyRole(ADMIN_ROLE, USER_ROLE)
				.antMatchers(HttpMethod.GET, SecConstatnts.USER_URL).hasAnyRole(USER_ROLE).and().csrf().disable().headers()
				.frameOptions().disable();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser(username1).password(passwordEncoder().encode(password)).roles(USER_ROLE).and()
				.withUser(admin_name).password(admin_password).roles(ADMIN_ROLE).and().withUser(username2)
				.password(password).roles(USER_ROLE);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
