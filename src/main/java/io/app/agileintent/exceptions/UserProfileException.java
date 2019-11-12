package io.app.agileintent.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserProfileException extends RuntimeException {


	private static final long serialVersionUID = -2178444210770570534L;
	
	private String userExecetpionMessage;

	public UserProfileException(String userExecetpionMessage) {
		this.userExecetpionMessage = userExecetpionMessage;
	}

	public String getUserExecetpionMessage() {
		return userExecetpionMessage;
	}

	public void setUserExecetpionMessage(String userExecetpionMessage) {
		this.userExecetpionMessage = userExecetpionMessage;
	}


	
}
