package io.app.agileintent.service;

import io.app.agileintent.exceptions.UserProfileException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import io.app.agileintent.domain.User;

@Service
public interface EmailConfirmationService {
    void sendRegistrationEmail(User newUSer);

    User activateAccount(String token);

    @Async
    void sendPasswordResetEmail(User user);

    User resetPassword(User user, String resetToken) throws UserProfileException;
}
      