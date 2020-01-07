package io.app.agileintent.service;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Service;

import io.app.agileintent.domain.User;

@Service
public interface UserService {

	User save(User user);
	List<User> getAllUsers(Principal principal);
	
}
