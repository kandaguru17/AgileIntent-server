package io.app.agileintent.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import io.app.agileintent.domain.EmailConfirmation;
import io.app.agileintent.domain.User;
import io.app.agileintent.repositories.EmailConfirmationRepository;
import io.app.agileintent.service.EmailService;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

	@Autowired
	private EmailConfirmationRepository emailConfirmationRepository;

	@Autowired
	private EmailService emailService;

	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent onRegistrationCompleteEvent) {

		String mailBody = "To Activate your Account, Please click the following link.\n "
				+ "Note that this link would be active only for an hour from now.\n Activation URL :";

		User user = onRegistrationCompleteEvent.getUser();
		EmailConfirmation emailConfirmation = new EmailConfirmation();
		user.addEmail(emailConfirmation);
		EmailConfirmation savedEmailConfirmation = emailConfirmationRepository.save(emailConfirmation);

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(user.getUsername());
		mailMessage.setSubject("Complete Registration!");
		mailMessage.setFrom("agileintent.business@gmail.com");
		mailMessage.setText(mailBody + onRegistrationCompleteEvent.getAppUrl() + "?token="
				+ savedEmailConfirmation.getConfirmationToken());

		emailService.sendEmail(mailMessage);

	}

}
