package io.app.agileintent.event;

import io.app.agileintent.domain.ConfirmationToken;
import io.app.agileintent.exceptions.UserProfileException;
import io.app.agileintent.repositories.ConfirmationTokenRepository;
import io.app.agileintent.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class PasswordResetEventListener implements ApplicationListener<PasswordResetEvent> {

    @Autowired
    private EmailService emailService;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public void onApplicationEvent(PasswordResetEvent event) {

        String mailBody = "To reset your Account Password, Please click the following link.\n "
                + "Note that this link would be active only for an hour from now.\n Password Reset URL :";

        ConfirmationToken passwordResetToken = new ConfirmationToken();
        passwordResetToken.setUser(event.getUser());
        ConfirmationToken savedPasswordResetToken = confirmationTokenRepository.save(passwordResetToken);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("Password Reset");
        simpleMailMessage.setTo(event.getUser().getUsername());
        simpleMailMessage.setFrom("agileintent.business@gmail.com");
        simpleMailMessage.setText(mailBody + event.getPasswordResetUrl() + "?token="
                + savedPasswordResetToken.getConfirmationToken());

        emailService.sendEmail(simpleMailMessage);
    }
}
