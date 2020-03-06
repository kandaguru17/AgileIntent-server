package io.app.agileintent.service.impl;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import io.app.agileintent.domain.User;
import io.app.agileintent.exceptions.UserProfileException;
import io.app.agileintent.repositories.UserRepository;
import io.app.agileintent.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	

	@Override
	public User registerUser(User user) {

		// username had to be unique
		User foundUser = userRepository.findByUsername(user.getUsername());
		if (foundUser != null)
			throw new UserProfileException(
					"User name with " + user.getUsername() + " already exists.Please try a different one");

		// check pwd with confirm pwd
		if (!user.getPassword().equals(user.getConfirmPassword()))
			throw new UserProfileException("Password do not match!");

		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//		user.setConfirmPassword(null);
		user.setFirstName(user.getFirstName().substring(0,1).toUpperCase() +user.getFirstName().substring(1));
		user.setLastName(user.getLastName().substring(0,1).toUpperCase() +user.getLastName().substring(1));

		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUsers(Principal principal) {
		
		User user = userRepository.findByUsername(principal.getName());
		if (user == null)
			throw new UserProfileException("user not found exception");

		return userRepository.findAll();
	}




}
