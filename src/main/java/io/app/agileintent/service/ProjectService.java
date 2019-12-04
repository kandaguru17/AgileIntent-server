package io.app.agileintent.service;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Service;

import io.app.agileintent.domain.Project;

@Service
public interface ProjectService {

	public Project addProject(Project project,Principal principal);
	public Project updateProject(Project project,String projectIdentifier,Principal principal);
	public Project getProjectByProjectIdentifier(String projectIdentifier,Principal principal);
	public List<Project> getAllProjects(Principal principal);
	public void deleteProjectByProjectIdentifier(String projectIdentifier,Principal principal);

}
