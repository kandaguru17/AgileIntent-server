package io.app.agileintent.service.impl;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.app.agileintent.domain.Project;
import io.app.agileintent.domain.User;
import io.app.agileintent.exceptions.UserProfileException;
import io.app.agileintent.repositories.ProjectRepository;
import io.app.agileintent.repositories.UserRepository;
import io.app.agileintent.service.ProjectMemberService;
import io.app.agileintent.service.ProjectService;

@Service
public class ProjectMemberServiceImpl implements ProjectMemberService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private ProjectService projectService;
	
	
	public Project addUserToProject(String projectIdentifier, String username, Principal principal) {

		User user = userRepository.findByUsername(username);
		Project project = projectService.getProjectByProjectIdentifier(projectIdentifier, principal);

		if (user == null)
			throw new UserProfileException("No such user registered.");

		if (!principal.getName().equalsIgnoreCase(project.getReportingPerson())) {
			throw new UserProfileException("Insufficient Priviledges, Contact your Reporting Person");
		}

		if (principal.getName().equalsIgnoreCase(username)
				|| project.getUsers().stream().filter(o -> o.getUsername().equals(username)).findFirst().isPresent()) {
			throw new UserProfileException("Already a Member of the Project");
		}

		project.addUser(user);
		return projectRepository.save(project);
	}

	public List<User> getProjectUsers(String projectIdentifier, Principal principal) {
		Project project = projectService.getProjectByProjectIdentifier(projectIdentifier, principal);
		return project.getUsers();
	}
}