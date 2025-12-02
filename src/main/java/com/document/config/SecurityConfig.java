package com.document.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	
	@Bean
	public UserDetailsService userDetailsService() {
	    UserDetails user = User.withUsername("aish")
	            .password("{noop}1234")
	            .roles("USER")
	            .build();

	    return new InMemoryUserDetailsManager(user);
	}
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http)throws Exception {
		http.csrf().disable()
		.authorizeHttpRequests()
		.requestMatchers("/welcome").permitAll()
		.anyRequest().authenticated()
		.and()
		.httpBasic();
		
		
		return http.build();
	}
}
