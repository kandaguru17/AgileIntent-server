package io.app.agileintent.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import io.app.agileintent.exceptions.LoginException;

@Component
public class jwtAuthenticationEntryPoint implements AuthenticationEntryPoint  {
	
	
	//mehtod invoked when ever there is an bad authenitcation
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		LoginException loginException= new LoginException();
		
		String json=new Gson().toJson(loginException);
		
		response.setContentType("applicaiton/json");
		response.setStatus(401);
		
		response.getWriter().print(json);
		
	}

}
