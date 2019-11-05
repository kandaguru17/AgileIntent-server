package io.app.agileintent.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.app.agileintent.domain.Backlog;
import io.app.agileintent.domain.ProjectTask;
import io.app.agileintent.exceptions.ProjectNotFoundException;
import io.app.agileintent.repositories.BacklogRepository;
import io.app.agileintent.repositories.ProjectRepository;
import io.app.agileintent.repositories.ProjectTaskRepository;
import io.app.agileintent.service.ProjectTaskService;

@Service
public class ProjectTaskServiceImpl implements ProjectTaskService {

	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	@Autowired
	private BacklogRepository backlogRepository;
	@Autowired
	private ProjectRepository projectRepository;

	@Override
	public ProjectTask addProjectTaskToBacklog(String projectIdentifier, ProjectTask projectTask) {

		try {
			Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);

			int currentSequenceNumber = backlog.getProjectTaskSequenceTracker() + 1;

			backlog.setProjectTaskSequenceTracker(currentSequenceNumber);

			projectTask.setProjectTaskSequence(projectIdentifier + "-" + currentSequenceNumber);
			projectTask.setProjectIdentifier(projectIdentifier);

			if (projectTask.getStatus() == null || projectTask.getStatus().trim() == "")
				projectTask.setStatus("TO_DO");

			if (projectTask.getPriority() == null || projectTask.getPriority() == 0)
				projectTask.setPriority(3);

			// bi-direction relationship
			backlog.addProjectTask(projectTask);

			// cascade refresh updates the backlog db accordingly
			return projectTaskRepository.save(projectTask);

		} catch (Exception e) {

			// e.printStackTrace();
			throw new ProjectNotFoundException("Backlog for the project ID " + projectIdentifier + " not found");

		}

	}

	@Override
	public List<ProjectTask> getAllProjectTasks(String projectIdentifier) {
		Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
		if (backlog == null)
			throw new ProjectNotFoundException("Backlog with Project ID " + projectIdentifier + " not found.");

		return projectTaskRepository.findAllByProjectIdentifierOrderByPriority(projectIdentifier);
	}

	@Override
	public ProjectTask getProjectTaskByProjectTaskSequence(String projectIdentifier, String projectTaskSequence) {

		Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
		if (backlog == null)
			throw new ProjectNotFoundException("Backlog with Project ID " + projectIdentifier + " not found.");

		ProjectTask foundProjectTask = projectTaskRepository.findByProjectTaskSequence(projectTaskSequence);
		if (foundProjectTask == null)
			throw new ProjectNotFoundException("project task not found");
		

		if (!foundProjectTask.getProjectIdentifier().equals(projectIdentifier))
			throw new ProjectNotFoundException("Backlog id and project-task do not match");

		return foundProjectTask;

	}
	
	@Override
	public ProjectTask updateProjectTask(String projectIdentifier, String projectTaskSequence,ProjectTask updatedProjectTask) {
		

		ProjectTask foundProjectTask = getProjectTaskByProjectTaskSequence(projectIdentifier, projectTaskSequence);
		updatedProjectTask.setCreatedAt(foundProjectTask.getCreatedAt());
		return projectTaskRepository.save(updatedProjectTask);
		
		
	}

	

	@Override
	public void deleteProjectTask(String projectIdentifier, String projectTaskSequence) {

		Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
		if (backlog == null)
			throw new ProjectNotFoundException("Backlog with Project ID " + projectIdentifier + " not found.");

		ProjectTask foundProjectTask = projectTaskRepository.findByProjectTaskSequence(projectTaskSequence);
		if (foundProjectTask == null)
			throw new ProjectNotFoundException("project task not found");
		
		if (!foundProjectTask.getProjectIdentifier().equals(projectIdentifier))
			throw new ProjectNotFoundException("project-task "+projectTaskSequence+" does not exist in project " +backlog.getProjectIdentifier() +" do not match");;


		System.out.println(foundProjectTask);

		//breaking the association (bi-directional relationship)
		backlog.removeProjectTask(foundProjectTask);
		
		projectTaskRepository.delete(foundProjectTask);

	}


}
