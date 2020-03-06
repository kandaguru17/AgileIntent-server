package io.app.agileintent.event;

import org.springframework.context.ApplicationEvent;

import io.app.agileintent.domain.User;

public class OnRegistrationCompleteEvent extends ApplicationEvent {


    private static final long serialVersionUID = 1L;

    private String appUrl;
    private User user;

    public OnRegistrationCompleteEvent( User user,String appUrl) {
        super(user);
        this.appUrl = appUrl;
        this.user = user;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
