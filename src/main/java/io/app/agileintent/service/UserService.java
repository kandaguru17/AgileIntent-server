package io.app.agileintent.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import io.app.agileintent.domain.User;

@Service
public interface UserService {

	User save(@Valid User user);
	
}
