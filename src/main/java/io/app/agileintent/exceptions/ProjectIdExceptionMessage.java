package io.app.agileintent.exceptions;


public class ProjectIdExceptionMessage {

	@Override
	public String toString() {
		return "ProjectIdExceptionResponse [projectIdentifier=" + projectIdentifier + "]";
	}

	private String projectIdentifier;
	
	public ProjectIdExceptionMessage(String projectIdentifier) {
		this.projectIdentifier=projectIdentifier;
	}

	public String getProjectIdentifier() {
		return projectIdentifier;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}
	
	
}
