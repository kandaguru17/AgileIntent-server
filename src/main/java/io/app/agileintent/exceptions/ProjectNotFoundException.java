package io.app.agileintent.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1055843228249687011L;

	String backlogExceptionMessage;

	public ProjectNotFoundException(String backlogExceptionMessage) {
		this.backlogExceptionMessage = backlogExceptionMessage;
	}

	public String getBacklogExceptionMessage() {
		return backlogExceptionMessage;
	}

	public void setBacklogExceptionMessage(String backlogExceptionMessage) {
	
		this.backlogExceptionMessage = backlogExceptionMessage;
	}

	@Override
	public String toString() {
		return "ProjectNotFoundException [backlogExceptionMessage=" + backlogExceptionMessage + "]";
	}
}
