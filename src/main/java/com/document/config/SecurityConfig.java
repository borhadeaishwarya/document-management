package com.document.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.document.security.JwtFilter;

@Configuration
public class SecurityConfig {
	@Autowired
	private JwtFilter jwtfilter;
	//this is for basic auth not for jwt security
//	@Bean
//	public UserDetailsService userDetailsService() {
//	    UserDetails user = User.withUsername("aish")
//	            .password("{noop}1234")
//	            .roles("USER")
//	            .build();
//
//	    return new InMemoryUserDetailsManager(user);
//	}
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http)throws Exception {
		http.csrf().disable()
		.authorizeHttpRequests()
		.requestMatchers("/welcome","/auth").permitAll()
		.anyRequest().authenticated()
		.and()
		.sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	//add jwt filter
		http.addFilterBefore(jwtfilter, UsernamePasswordAuthenticationFilter.class);
		
		
		return http.build();
	}
}
