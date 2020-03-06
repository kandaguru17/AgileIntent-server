package io.app.agileintent.event;

import io.app.agileintent.domain.ConfirmationToken;
import io.app.agileintent.domain.User;
import io.app.agileintent.repositories.ConfirmationTokenRepository;
import io.app.agileintent.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent onRegistrationCompleteEvent) {

        String mailBody = "To Activate your Account, Please click the following link.\n "
                + "Note that this link would be active only for an hour from now.\n Activation URL :";

        User user = onRegistrationCompleteEvent.getUser();
        ConfirmationToken confirmationToken = new ConfirmationToken();
        user.addConfirmationToken(confirmationToken);
        ConfirmationToken savedConfirmationToken = confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getUsername());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("agileintent.business@gmail.com");
        mailMessage.setText(mailBody + onRegistrationCompleteEvent.getAppUrl() + "?token="
                + savedConfirmationToken.getConfirmationToken());

        emailService.sendEmail(mailMessage);

    }

}
