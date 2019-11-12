package io.app.agileintent.service.impl;

import javax.validation.Valid;

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
	UserRepository userRepiository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public User save(@Valid User user){
		
		//username had to be unique
		User foundUser=userRepiository.findByUsername(user.getUsername());
		if(foundUser!=null)
			throw new UserProfileException("User name with "+user.getUsername()+" already exists.Please try a different one");
		
		//check pwd with confirm pwd
		if(!user.getPassword().equals(user.getConfirmPassword()))
			throw new UserProfileException("Password do not match!"); 
		
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setConfirmPassword(null);

		return userRepiository.save(user);
	}

}
