package io.app.agileintent.security;

import static io.app.agileintent.security.SecurityConstants.HEADER;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.app.agileintent.domain.User;
import io.app.agileintent.service.CustomUserDetailService;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	CustomUserDetailService customUserDetailService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		
		try {
			
			String token = request.getHeader(HEADER);
			String jwt = token.substring(7, token.length());

			if (!(jwt.equals(null)) && jwtTokenProvider.ValidateToken(jwt)) {

				String username = jwtTokenProvider.getUserNameFromJwt(jwt);
				User user = (User) customUserDetailService.loadUserByUsername(username);

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null,
						Collections.emptyList());

				
				// Loading the session to the authentication object -- not required for jwt authorization
				/*
				 * authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				 */
				
				//manually authenticating the users
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			logger.warn("issues with the spring security context");
		}
		
		filterChain.doFilter(request, response);

	}

}
