package io.app.agileintent.exceptions;

public class ProjectNotFoundExceptionMessage {

	private String ProjectNotFound;

	public ProjectNotFoundExceptionMessage(String ProjectNotFound) {
		this.ProjectNotFound = ProjectNotFound;
	}

	public String getProjectNotFound() {
		return ProjectNotFound;
	}

	public void setProjectNotFound(String ProjectNotFound) {
		this.ProjectNotFound = ProjectNotFound;
	}

	@Override
	public String toString() {
		return "BacklogExceptionMessage [ProjectNotFound=" + ProjectNotFound + "]";
	}

	
}
