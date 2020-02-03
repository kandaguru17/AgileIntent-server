package io.app.agileintent.service;

import org.springframework.stereotype.Service;

import io.app.agileintent.domain.User;

@Service
public interface EmailConfirmationService {
	public void sendRegistrationEmail(User newUSer);
	public void sendPasswordConfirmationEmail();
	public User activateAccount(String token); 
}
      