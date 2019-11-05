package io.app.agileintent.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.app.agileintent.domain.Backlog;
import io.app.agileintent.domain.Project;
import io.app.agileintent.exceptions.ProjectIdException;
import io.app.agileintent.repositories.BacklogRepository;
import io.app.agileintent.repositories.ProjectRepository;
import io.app.agileintent.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private BacklogRepository backlogRepository;

	public Project addOrUpdateProject(Project project) {
		
		String projectIdentifier=project.getProjectIdentifier().toUpperCase();

		try {

			Project foundProject = projectRepository
					.findByProjectIdentifier(projectIdentifier);
			
			if(foundProject ==null) {
				Backlog backlog= new Backlog();
				backlog.setProjectIdentifier(projectIdentifier);
				//setting up the bidirectional relationship
				project.addBackLog(backlog);
			}
			
			if (foundProject != null) {
				project.setCreatedAt(foundProject.getCreatedAt());
				project.setBacklog(backlogRepository.findByProjectIdentifier(projectIdentifier));
				
			}
			
		
					
			//saves backlog as well due to cascading
			return projectRepository.save(project);

		} catch (Exception e) {
			
			e.printStackTrace();
			throw new ProjectIdException(
					"Project ID  " + project.getProjectIdentifier().toUpperCase() + "already exists");
		}

	}

	public Project getProjectByProjectIdentifier(String projectIdentifier) {

		Project project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
		if (project == null)
			throw new ProjectIdException("Project Id " + projectIdentifier + " does not exist");

		return project;
	}

	public List<Project> getAllProjects() {
		return projectRepository.findAll();
	}

	public void deleteProjectByProjectIdentifier(String projectIdentifier) {

		Project project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
		if (project == null)
			throw new ProjectIdException("Project with ID '" + projectIdentifier + "' doesnt exist");

		projectRepository.delete(project);

	}

}
