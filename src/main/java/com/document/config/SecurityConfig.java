package com.document.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.document.security.JwtAuthEntryPoint;
import com.document.security.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


	@Autowired
	private JwtAuthEntryPoint jwtAuthEntryPoint;

	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user = User.withUsername("aish").password("{noop}1234").roles("USER").build();
		return new InMemoryUserDetailsManager(user);
	}

//)
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {

		http.csrf(csrf -> csrf.disable()).exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthEntryPoint))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/api/document/login", "/api/document/welcome")
						.permitAll().anyRequest().authenticated());

		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}