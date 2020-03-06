package io.app.agileintent.model;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class UsernameModel implements Serializable {

    @NotBlank(message = "User name is mandatory")
    private String username;

    public UsernameModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
