package io.app.agileintent.service.impl;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.app.agileintent.domain.Backlog;
import io.app.agileintent.domain.Project;
import io.app.agileintent.domain.ProjectTask;
import io.app.agileintent.exceptions.ProjectNotFoundException;
import io.app.agileintent.repositories.ProjectTaskRepository;
import io.app.agileintent.service.ProjectService;
import io.app.agileintent.service.ProjectTaskService;

@Service
public class ProjectTaskServiceImpl implements ProjectTaskService {

	@Autowired
	private ProjectTaskRepository projectTaskRepository;

	@Autowired
	private ProjectService projectService;

	@Override
	public ProjectTask addProjectTaskToBacklog(String projectIdentifier, ProjectTask projectTask,Principal principal) {
		
		Project foundProject=projectService.getProjectByProjectIdentifier(projectIdentifier, principal);

		try {		
			
			Backlog backlog = foundProject.getBacklog();
			
			int currentSequenceNumber = backlog.getProjectTaskSequenceTracker() + 1;

			backlog.setProjectTaskSequenceTracker(currentSequenceNumber);

			projectTask.setProjectTaskSequence(projectIdentifier + "-" + currentSequenceNumber);
			projectTask.setProjectIdentifier(projectIdentifier);

			if (projectTask.getStatus() == null || projectTask.getStatus().trim() == "")
				projectTask.setStatus("TO_DO");

			if (projectTask.getPriority() == null || projectTask.getPriority() == 0)
				projectTask.setPriority(4);
			
			projectTask.setIssueType(projectTask.getIssueType().toUpperCase());
			// bi-direction relationship
			backlog.addProjectTask(projectTask);

			// cascade all updates the backlog db accordingly
			return projectTaskRepository.save(projectTask);

		} catch (Exception e) {

			 e.getMessage();
			throw new ProjectNotFoundException("Backlog for the project ID " + projectIdentifier + " not found");

		}

	}

	@Override
	public List<ProjectTask> getAllProjectTasks(String projectIdentifier,Principal principal) {
		
		projectService.getProjectByProjectIdentifier(projectIdentifier, principal);
		return projectTaskRepository.findAllByProjectIdentifierOrderByPriority(projectIdentifier);
	}

	@Override
	public ProjectTask getProjectTaskByProjectTaskSequence(String projectIdentifier, String projectTaskSequence,Principal principal) {

		projectService.getProjectByProjectIdentifier(projectIdentifier, principal);
		
		ProjectTask foundProjectTask = projectTaskRepository.findByProjectTaskSequence(projectTaskSequence);
		if (foundProjectTask == null)
			throw new ProjectNotFoundException("project task not found");
		
		if (!foundProjectTask.getProjectIdentifier().equals(projectIdentifier))
			throw new ProjectNotFoundException("Backlog id and project-task do not match");

		return foundProjectTask;

	}
	
	@Override
	public ProjectTask updateProjectTask(String projectIdentifier, String projectTaskSequence,ProjectTask projectTask,Principal principal) {
		
		ProjectTask foundProjectTask = getProjectTaskByProjectTaskSequence(projectIdentifier, projectTaskSequence,principal);		
		projectTask.setId(foundProjectTask.getId());
		
		if (projectTask.getStatus() == null || projectTask.getStatus().trim() == "")
			projectTask.setStatus("TO_DO");

		if (projectTask.getPriority() == null || projectTask.getPriority() == 0)
			projectTask.setPriority(4);
		
		projectTask.setIssueType(projectTask.getIssueType().toUpperCase());
		
//		have set the column updatable =false
//		projectTask.setBacklog(foundProjectTask.getBacklog());
//		projectTask.setCreatedAt(foundProjectTask.getCreatedAt());
//		projectTask.setProjectIdentifier(foundProjectTask.getProjectIdentifier());
//		projectTask.setProjectTaskSequence(foundProjectTask.getProjectTaskSequence());
		
		return projectTaskRepository.save(projectTask);	
	}

	@Override
	public void deleteProjectTask(String projectIdentifier, String projectTaskSequence,Principal principal) {
		
		ProjectTask foundProjectTask = getProjectTaskByProjectTaskSequence(projectIdentifier, projectTaskSequence, principal);
		Backlog backlog = foundProjectTask.getBacklog();
		
		//breaking the association (bi-directional relationship)
		backlog.removeProjectTask(foundProjectTask);
		
		projectTaskRepository.delete(foundProjectTask);

	}


}
