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
import io.app.agileintent.repositories.BacklogRepository;
import io.app.agileintent.repositories.ProjectRepository;
import io.app.agileintent.repositories.UserRepository;
import io.app.agileintent.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private BacklogRepository backlogRepository;

	@Autowired
	private UserRepository userRepository;

	public Project addOrUpdateProject(Project project, Principal principal) throws ProjectIdException {

		String projectIdentifier = project.getProjectIdentifier().toUpperCase();
		User user = userRepository.findByUsername(principal.getName());

		Project foundProject = projectRepository.findByProjectIdentifier(projectIdentifier);
		System.out.println(foundProject);

		if (foundProject!=null
				&& (!foundProject.getReportingPerson().equals(user.getFirstName() + " " + user.getLastName()))) {
			throw new UserProfileException("Not Authorised to update Project with Id " + projectIdentifier);
		}

		try {
			
			if (foundProject != null) {
				project.setCreatedAt(foundProject.getCreatedAt());
				project.setBacklog(backlogRepository.findByProjectIdentifier(projectIdentifier));
			}

			if (foundProject == null) {
				Backlog backlog = new Backlog();
				backlog.setProjectIdentifier(projectIdentifier);
				// setting up the bidirectional relationship between project and backlog
				project.addBackLog(backlog);
				// setting up bidirectional relationship between project and user
				user.addProject(project);
				
			}
			
			
			project.setReportingPerson(user.getFirstName() + " " + user.getLastName());
			System.out.println("===> " +user.getFirstName() + " " + user.getLastName());
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
		if (!project.getReportingPerson().equals(user.getFirstName() + " " + user.getLastName())) {
			throw new ProjectIdException("Not Authorised to access Project with Id " + projectIdentifier);
		}

		return project;
	}
	
	
	
//	public  Project UpdateProject(Project project,Principal principal) {
//			
//		Project foundProject= getProjectByProjectIdentifier(project.getProjectIdentifier(), principal);
//		projectRepository.save(project);
//	}
	

	public List<Project> getAllProjects(Principal principal) {
		User user = userRepository.findByUsername(principal.getName());
		return projectRepository.findAllByReportingPerson(user.getFirstName() + " " + user.getLastName());
	}

	public void deleteProjectByProjectIdentifier(String projectIdentifier, Principal principal) {

		Project project = projectRepository.findByProjectIdentifier(projectIdentifier);
		if (project == null)
			throw new ProjectIdException("Project Id " + projectIdentifier + " does not exist");

		User user = userRepository.findByUsername(principal.getName());
		if (!project.getReportingPerson().equals(user.getFirstName() + " " + user.getLastName())) {
			throw new ProjectIdException("Not Authorised to delete Project with Id " + projectIdentifier);
		}

		user.removeProject(project);
		projectRepository.delete(project);
	}
}
