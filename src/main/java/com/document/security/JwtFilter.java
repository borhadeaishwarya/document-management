package com.document.security;

import java.io.IOException;
import java.security.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


//it checks token in request,validate extract username,per request run 
@Component
public class JwtFilter extends OncePerRequestFilter {
  @Autowired
	private JwtSecure jwtSecure;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String header = request.getHeader("Authorization");
		String token=null;
		String username=null;
		
		if(header!=null && header.startsWith("Bearer ")) {
			token=header.substring(7);
			username=jwtSecure.extractUsername(token);	
		}
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
		{
			if(jwtSecure.validateToken(token)) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,null,null);
			
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
	filterChain.doFilter(request,response);
	}
}

