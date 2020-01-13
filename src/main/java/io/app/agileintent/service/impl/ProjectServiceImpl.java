package io.app.agileintent.service.impl;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.app.agileintent.domain.Backlog;
import io.app.agileintent.domain.Project;
import io.app.agileintent.domain.User;
import io.app.agileintent.exceptions.ProjectIdException;
import io.app.agileintent.exceptions.UserProfileException;
import io.app.agileintent.repositories.ProjectRepository;
import io.app.agileintent.repositories.UserRepository;
import io.app.agileintent.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private UserRepository userRepository;

	public Project addProject(Project project, Principal principal) throws ProjectIdException {

		String projectIdentifier = project.getProjectIdentifier().toUpperCase();
		User user = userRepository.findByUsername(principal.getName());
		Project foundProject = projectRepository.findByProjectIdentifier(projectIdentifier);

		try {
			if (foundProject == null) {

				// a project must have a backlog
				Backlog backlog = new Backlog();
				backlog.setProjectIdentifier(projectIdentifier);
				// setting up the bidirectional relationship between project and backlog
				project.addBackLog(backlog);
				// setting up bidirectional relationship between project and user
				user.addProject(project);
				// setting the reporting person to current user
				project.setReportingPerson(user.getUsername());
			}

			// saves backlog as well due to cascading
			return projectRepository.save(project);

		} catch (Exception e) {

			e.printStackTrace();
			throw new ProjectIdException("Project with  ID" + projectIdentifier + " already exists");
		}
	}

	public Project getProjectByProjectIdentifier(String projectIdentifier, Principal principal) {

		Project project = projectRepository.findByProjectIdentifier(projectIdentifier);
		if (project == null)
			throw new ProjectIdException("Project Id " + projectIdentifier + " does not exist");

		User user = userRepository.findByUsername(principal.getName());
		if (user == null)
			throw new UserProfileException("No such user");

		boolean isValidUser = project.getUsers().contains(user);
		if (!isValidUser) {
			throw new ProjectIdException("Not Authorised to access Project with Id " + projectIdentifier);
		}

		return project;
	}

	public Project updateProject(Project project, String projectIdentifier, Principal principal) {

		Project foundProject = getProjectByProjectIdentifier(projectIdentifier, principal);
//		project.setId(foundProject.getId());
//		project.addBackLog(foundProject.getBacklog());

		foundProject.setDescription(project.getDescription());
		foundProject.setProjectName(project.getProjectName());

		if (project.getStartDate() != null)
			foundProject.setStartDate(project.getStartDate());
		if (project.getEndDate() != null)
			foundProject.setEndDate(project.getEndDate());

		// refactoring required

		/*
		 * made the column (updatable=false)
		 * project.setCreatedAt(foundProject.getCreatedAt());
		 * project.setReportingPerson(foundProject.getReportingPerson());
		 * project.setUser(foundProject.getUser());
		 */

		return projectRepository.save(foundProject);

	}

	public List<Project> getAllProjects(Principal principal) {

		User user = userRepository.findByUsername(principal.getName());
		if (user == null)
			throw new UserProfileException("No such user found");

		return projectRepository.findAllProjects(user.getId());
	}

	public void deleteProjectByProjectIdentifier(String projectIdentifier, Principal principal) {
		Project project = getProjectByProjectIdentifier(projectIdentifier, principal);
		User user = userRepository.findByUsername(principal.getName());
		// breaking the bi-directional link
		user.removeProject(project);
		projectRepository.delete(project);
	}
}
