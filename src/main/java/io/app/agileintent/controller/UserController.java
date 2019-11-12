package io.app.agileintent.controller;

import java.text.ParseException;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.app.agileintent.domain.User;
import io.app.agileintent.security.AuthenticationRequest;
import io.app.agileintent.security.AuthenticationResponse;
import io.app.agileintent.security.JwtTokenProvider;
import io.app.agileintent.service.ErrorMapService;
import io.app.agileintent.service.UserService;
import static io.app.agileintent.security.SecurityConstants.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	UserService userService;
	@Autowired
	ErrorMapService errorMapService;
	@Autowired
	JwtTokenProvider jwtProvider;
	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping({ "/authenticate" })
	public ResponseEntity<?> getJwtToken(@Valid @RequestBody AuthenticationRequest authenticationRequest,
			BindingResult result) throws ParseException {

		ResponseEntity<Map<String, String>> errors = errorMapService.mapErrors(result);
		if (errors != null)
			return errors;

		Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				authenticationRequest.getUsername(), authenticationRequest.getPassword()));

		String jwt=jwtProvider.generateToken(auth);
		
		AuthenticationResponse authenticationResponse= new AuthenticationResponse(true,JWT_PREFIX + jwt);
		
		return new ResponseEntity<AuthenticationResponse>(authenticationResponse,HttpStatus.OK);

	}

	@PostMapping({ "/register" })
	public ResponseEntity<?> authenitcate(@Valid @RequestBody User user, BindingResult result) {

		ResponseEntity<Map<String, String>> error = errorMapService.mapErrors(result);
		if (error != null)
			return error;

		User newUser = userService.save(user);

		return new ResponseEntity<User>(newUser, HttpStatus.CREATED);

	}

}
