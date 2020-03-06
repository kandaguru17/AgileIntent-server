package io.app.agileintent.model;

import io.app.agileintent.validators.ValidPassword;

import java.io.Serializable;

public class PasswordModel implements Serializable {

    @ValidPassword
    private String password;
    @ValidPassword
    private String confirmPassword;

    public PasswordModel() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
