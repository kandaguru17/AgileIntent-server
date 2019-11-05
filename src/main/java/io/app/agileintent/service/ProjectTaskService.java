package io.app.agileintent.service;

import java.util.List;

import org.springframework.stereotype.Service;

import io.app.agileintent.domain.ProjectTask;

@Service
public interface ProjectTaskService {

	ProjectTask addProjectTaskToBacklog(String projectIdentifier, ProjectTask projectTask);

	List<ProjectTask> getAllProjectTasks(String projectIdentifier);

	ProjectTask getProjectTaskByProjectTaskSequence(String projectIdentifier, String projectTaskSequence);

	void deleteProjectTask(String projectIdentifier, String projectTaskSequence);

	ProjectTask updateProjectTask(String projectIdentifier, String projectTaskSequence, ProjectTask updatedProjectTask);

}
