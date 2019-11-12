package io.app.agileintent.exceptions;

public class UserProfileExceptionMessage {

	private String userProfile;

	public UserProfileExceptionMessage(String userProfile) {
		this.userProfile = userProfile;
	}

	public String getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(String userProfile) {
		this.userProfile = userProfile;
	}
	

}
