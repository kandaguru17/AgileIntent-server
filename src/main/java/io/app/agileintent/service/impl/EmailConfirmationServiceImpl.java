package io.app.agileintent.service.impl;

import io.app.agileintent.domain.ConfirmationToken;
import io.app.agileintent.domain.User;
import io.app.agileintent.event.OnRegistrationCompleteEvent;
import io.app.agileintent.event.PasswordResetEvent;
import io.app.agileintent.exceptions.UserProfileException;
import io.app.agileintent.repositories.ConfirmationTokenRepository;
import io.app.agileintent.repositories.UserRepository;
import io.app.agileintent.service.EmailConfirmationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EmailConfirmationServiceImpl implements EmailConfirmationService {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${agileintent.registration.url}")
    private String confirmAccountUrl;

    @Value("${agileintent.password-reset.url}")
    private String passwordResetUrl;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Async
    public void sendRegistrationEmail(User newUser) {

        try {
            applicationEventPublisher.publishEvent(new OnRegistrationCompleteEvent(newUser, confirmAccountUrl));
        } catch (Exception e) {

            System.err.println(e.getMessage());
        }
    }


    @Override
    @Async
    public void sendPasswordResetEmail(User existingUser) {
        existingUser.setEnabled(false);
        try {
            applicationEventPublisher.publishEvent(new PasswordResetEvent(existingUser, passwordResetUrl));
        } catch (Exception e) {

            System.err.println(e.getMessage());
        }
    }

    public User activateAccount(String token) {

        ConfirmationToken confirmationToken = confirmationTokenRepository.findByConfirmationToken(token);

        if (confirmationToken == null)
            throw new UserProfileException("Invalid Activation Link");

        Date current = new Date();
        Date expiryDate = confirmationToken.getExpiresAt();
        User registeredUser = confirmationToken.getUser();

        if (expiryDate.compareTo(current) < 0) {
            registeredUser.removeConfirmationToken(confirmationToken);
            throw new UserProfileException("Activation Link expired");
        }

        if (registeredUser.isEnabled())
            throw new UserProfileException("Account already activated");

        registeredUser.setEnabled(true);

        confirmationTokenRepository.delete(confirmationToken);

        return userRepository.save(registeredUser);

    }


    @Override
    public User resetPassword(User user, String resetToken) throws UserProfileException {

        ConfirmationToken confirmationToken = confirmationTokenRepository.findByConfirmationToken(resetToken);

        if (confirmationToken == null)
            throw new UserProfileException("Invalid Activation Link");

        User foundUser = confirmationToken.getUser();

        if (!user.getPassword().equals(user.getConfirmPassword()))
            throw new UserProfileException("Passwords do not match");

        Date current = new Date();
        Date expiryDate = confirmationToken.getExpiresAt();


        if (expiryDate.compareTo(current) < 0) {
            foundUser.removeConfirmationToken(confirmationToken);
            confirmationTokenRepository.delete(confirmationToken);
            throw new UserProfileException("Password reset Link expired");
        }

        foundUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        foundUser.setEnabled(true);
        confirmationTokenRepository.delete(confirmationToken);

        return userRepository.save(foundUser);

    }

}
