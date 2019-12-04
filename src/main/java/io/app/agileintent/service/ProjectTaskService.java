package io.app.agileintent.service;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Service;

import io.app.agileintent.domain.ProjectTask;

@Service
public interface ProjectTaskService {

	ProjectTask addProjectTaskToBacklog(String projectIdentifier, ProjectTask projectTask,Principal principal);

	List<ProjectTask> getAllProjectTasks(String projectIdentifier,Principal principal);

	ProjectTask getProjectTaskByProjectTaskSequence(String projectIdentifier, String projectTaskSequence,Principal principal);

	void deleteProjectTask(String projectIdentifier, String projectTaskSequence,Principal principal);

	ProjectTask updateProjectTask(String projectIdentifier, String projectTaskSequence, ProjectTask updatedProjectTask,Principal principal);

}
