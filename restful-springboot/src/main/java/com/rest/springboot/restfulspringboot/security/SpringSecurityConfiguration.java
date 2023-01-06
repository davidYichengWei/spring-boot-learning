package com.rest.springboot.restfulspringboot.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SpringSecurityConfiguration {
	
	// Overwrite the default filter chain to define custom authentication
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		// Authenticate all requests
		http.authorizeHttpRequests(
			auth -> auth.anyRequest().authenticated()
			);
		
		// Use basic authentiction
		http.httpBasic(withDefaults());
		
		// Disable CSRF check to allow update without CSRF token
		http.csrf().disable();
		
		return http.build();
	}
}
