package io.app.agileintent.service;

import java.security.Principal;
import java.util.List;

import io.app.agileintent.domain.User;

public interface ProjectMemberService {

	User addUserToProject(String projectIdentifier, String username, Principal principal);

	User removeUserFromProject(String projectIdentifier, String username, Principal principal);

	List<User> getProjectUsers(String projectIdentifier, Principal principal);

	User assignUserToProjectTask(String projectIdentifier, String projectTaskSequence, String username,
			Principal principal);

	List<User> getProjectUsersByFirstName(String projectIdentifier, String firstName, Principal principal);
}
