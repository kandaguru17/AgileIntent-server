package io.app.agileintent.service.impl;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.app.agileintent.domain.Project;
import io.app.agileintent.domain.ProjectTask;
import io.app.agileintent.domain.User;
import io.app.agileintent.exceptions.UserProfileException;
import io.app.agileintent.repositories.UserRepository;
import io.app.agileintent.service.ProjectMemberService;
import io.app.agileintent.service.ProjectService;
import io.app.agileintent.service.ProjectTaskService;

@Service
public class ProjectMemberServiceImpl implements ProjectMemberService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProjectService projectService;
	
	
	@Autowired
	private ProjectTaskService projectTaskService;

	public User addUserToProject(String projectIdentifier, String username, Principal principal) {

		User user = userRepository.findByUsername(username);
		Project project = projectService.getProjectByProjectIdentifier(projectIdentifier, principal);

		if (!principal.getName().equalsIgnoreCase(project.getReportingPerson())) {
			throw new UserProfileException("Insufficient Priviledges, Contact your Reporting Person");
		}
		
		if (user == null)
			throw new UserProfileException("No such user registered.");


		if (principal.getName().equalsIgnoreCase(username)
				|| project.getUsers().stream().filter(o -> o.getUsername().equals(username)).findFirst().isPresent()) {
			throw new UserProfileException("Already a Member of the Project");
		}

		user.addProject(project);
		return userRepository.save(user);
	}

	@Override
	public List<User> getProjectUsers(String projectIdentifier, Principal principal) {
		Project project = projectService.getProjectByProjectIdentifier(projectIdentifier, principal);
		return project.getUsers();
	}

	@Override
	public List<User> getProjectUsersByFirstName(String projectIdentifier,String firstName, Principal principal){
		Project project=projectService.getProjectByProjectIdentifier(projectIdentifier, principal);
		return userRepository.findUsersByMatchingFirstName(project.getId(), firstName); 
	}
	
	
	@Override
	public User removeUserfromProject(String projectIdentifier, String username, Principal principal) {

		User user = userRepository.findByUsername(username);
		Project project = projectService.getProjectByProjectIdentifier(projectIdentifier, principal);

		if (user == null)
			throw new UserProfileException("No such user registered.");

		if (!principal.getName().equalsIgnoreCase(project.getReportingPerson()))
			throw new UserProfileException("Insufficient Priviledges, Contact your Reporting Person");

		if (!project.getUsers().stream().filter(o -> o.getUsername().equals(username)).findFirst().isPresent())
			throw new UserProfileException("Not a Member of the Project");

		if (project.getReportingPerson().equalsIgnoreCase(username))
			throw new UserProfileException("Reporting Person cannot be removed");

		user.removeProject(project);
		return userRepository.save(user);

	}


	@Override
	public User assignUserToProjectTask(String projectIdentifier, String projectTaskSequence,String username, Principal principal) {
		
		ProjectTask projectTask=projectTaskService.getProjectTaskByProjectTaskSequence(projectIdentifier, projectTaskSequence, principal);
		User user=userRepository.findByUsername(username);
		
		if(user==null)
			throw new UserProfileException("No such user");
		
		List<User> users=getProjectUsers(projectIdentifier, principal);
		if(!users.contains(user))
			throw new UserProfileException("Not a member of this project,contact Reporting person");
		
		user.assignProjectTask(projectTask);
		userRepository.save(user);
		
		return userRepository.save(user);
	}


}
