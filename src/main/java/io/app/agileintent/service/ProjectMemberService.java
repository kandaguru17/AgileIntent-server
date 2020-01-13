package io.app.agileintent.service;

import java.security.Principal;
import java.util.List;

import io.app.agileintent.domain.User;

public interface ProjectMemberService {

	public User addUserToProject(String projectIdentifier,String username,Principal principal);
	public User removeUserfromProject(String projectIdentifier,String username,Principal principal);
	public List<User> getProjectUsers(String projectIdentifier,Principal principal);

	public User assignUserToProjectTask(String projectIdentifier,String projectTaskSequence,String username,Principal principal);
}
