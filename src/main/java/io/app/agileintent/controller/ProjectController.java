package io.app.agileintent.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.app.agileintent.domain.Project;
import io.app.agileintent.service.ErrorMapService;
import io.app.agileintent.service.ProjectService;

@CrossOrigin
@RestController
@RequestMapping("api/project")
public class ProjectController {

	
	@Autowired
	ProjectService projectService;

	@Autowired
	ErrorMapService errorMapService;

	@PostMapping({ "/", "" })
	public ResponseEntity<?> addNewProject(@Valid @RequestBody Project project, BindingResult result) {
		
		
		//request body validation
		ResponseEntity<Map<String, String>> errorMap = errorMapService.mapErrors(result);
		
		if (errorMap != null)
			return errorMap;

		project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
		Project savedProject = projectService.addOrUpdateProject(project);
		return new ResponseEntity<Project>(savedProject, HttpStatus.CREATED);
	}
	

	
	@GetMapping({"/{projectIdentifier}"})
	public ResponseEntity<Project> getProjectByProjectId(@PathVariable String projectIdentifier) {
		Project foundProject=projectService.getProjectByProjectIdentifier(projectIdentifier);
		return new ResponseEntity<Project>(foundProject,HttpStatus.OK);
		
	}
	
	
	@GetMapping({"","/"})
	public ResponseEntity<List<Project>> getAllProjects(){
		
		return new ResponseEntity<List<Project>>(projectService.getAllProjects(),HttpStatus.OK);
		
	}
	
	@DeleteMapping({"/{projectIdentifier}"})
	public ResponseEntity<?> deleteProjectbyProjectId(@PathVariable String projectIdentifier){
		
		projectService.deleteProjectByProjectIdentifier(projectIdentifier);
		return new ResponseEntity<String>("project successfully deleted",HttpStatus.OK);
	}


}

