package io.app.agileintent.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ProjectIdException extends RuntimeException {

	private static final long serialVersionUID = -5383458238443092388L;

	private String message;

	public ProjectIdException(String message) {
		this.setMessage(message);
	}

	public String getMessage() {
		return message;
	}	

	public void setMessage(String message) {
		this.message = message;
	}

}
