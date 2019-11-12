package io.app.agileintent.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.app.agileintent.domain.User;
import io.app.agileintent.repositories.UserRepository;
import io.app.agileintent.service.CustomUserDetailService;

@Service
public class CustomUserDetailServiceImpl implements CustomUserDetailService {

	@Autowired
	UserRepository userRepository;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User foundUser = userRepository.findByUsername(username);

		if (foundUser == null)
			new UsernameNotFoundException("Username does not Exist");

		return foundUser;
	}

	
	
}
