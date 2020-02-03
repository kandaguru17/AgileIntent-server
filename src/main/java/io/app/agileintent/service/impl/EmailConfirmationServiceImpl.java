package io.app.agileintent.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import io.app.agileintent.domain.EmailConfirmation;
import io.app.agileintent.domain.User;
import io.app.agileintent.event.OnRegistrationCompleteEvent;
import io.app.agileintent.exceptions.UserProfileException;
import io.app.agileintent.repositories.EmailConfirmationRepository;
import io.app.agileintent.repositories.UserRepository;
import io.app.agileintent.service.EmailConfirmationService;

@Service
public class EmailConfirmationServiceImpl implements EmailConfirmationService {

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Autowired
	private EmailConfirmationRepository emailConfirmationRepository;

	@Autowired
	private UserRepository userRepository;

	@Value("${agileintent.registration.url}")
	private String appUrl;

	@Override
	@Async
	public void sendRegistrationEmail(User newUser) {

		try {
			applicationEventPublisher.publishEvent(new OnRegistrationCompleteEvent(appUrl, newUser));
		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
	}

	public User activateAccount(String token) {

		EmailConfirmation emailconfirmation = emailConfirmationRepository.findByConfirmationToken(token);

		if (emailconfirmation == null)
			throw new UserProfileException("Invalid Activation Link");

		Date current = new Date();
		Date expiryDate = emailconfirmation.getExpiresAt();
		User registeredUser = emailconfirmation.getUser();

		if (expiryDate.compareTo(current) < 0) {
			registeredUser.removeEmail(emailconfirmation);
			userRepository.delete(registeredUser);
			throw new UserProfileException("Activation Link expired");
		}

		if (registeredUser.isEnabled())
			throw new UserProfileException("Account already activated");

		registeredUser.setEnabled(true);
		emailConfirmationRepository.delete(emailconfirmation);
		
		return userRepository.save(registeredUser);

	}

	@Override
	public void sendPasswordConfirmationEmail() {
		// TODO Auto-generated method stub

	}

}
