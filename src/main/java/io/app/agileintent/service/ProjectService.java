package io.app.agileintent.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.app.agileintent.domain.Backlog;
import io.app.agileintent.domain.Project;
import io.app.agileintent.exceptions.ProjectIdException;
import io.app.agileintent.repositories.BacklogRepository;
import io.app.agileintent.repositories.ProjectRepository;

@Service
public interface ProjectService {

	public Project addOrUpdateProject(Project project);
	public Project getProjectByProjectIdentifier(String projectIdentifier);
	public List<Project> getAllProjects();
	public void deleteProjectByProjectIdentifier(String projectIdentifier);

}
