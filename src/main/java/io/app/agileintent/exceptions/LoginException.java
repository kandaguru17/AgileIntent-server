package io.app.agileintent.exceptions;

public class LoginException {

	private String username;
	private String password;

	public LoginException() {

		this.username = "Invalid Credentials";
		this.password = "Invalid Credentials";

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
