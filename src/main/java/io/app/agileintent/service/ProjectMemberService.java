package io.app.agileintent.service;

import java.security.Principal;
import java.util.List;

import io.app.agileintent.domain.Project;
import io.app.agileintent.domain.User;

public interface ProjectMemberService {

	public Project addUserToProject(String projectIdentifier,String username,Principal principal);
	public List<User> getProjectUsers(String projectIdentifier,Principal principal);

}
