package io.app.agileintent.event;

import io.app.agileintent.domain.User;
import org.springframework.context.ApplicationEvent;

public class PasswordResetEvent extends ApplicationEvent {

    private User user;
    private String passwordResetUrl;


    /**
     * Create a new ApplicationEvent.
     *
     * @param passwordResetUrl,user the object on which the event initially occurred (never {@code null})
     */
    public PasswordResetEvent(User user, String passwordResetUrl) {
        super(user);
        this.user = user;
        this.passwordResetUrl = passwordResetUrl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPasswordResetUrl() {
        return passwordResetUrl;
    }

    public void setPasswordResetUrl(String passwordResetUrl) {
        this.passwordResetUrl = passwordResetUrl;
    }
}
