package io.app.agileintent.controller;

import java.security.Principal;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.app.agileintent.domain.ProjectTask;
import io.app.agileintent.service.ErrorMapService;
import io.app.agileintent.service.ProjectTaskService;

@RestController
@CrossOrigin
@RequestMapping("/api/backlog")
public class BacklogController {

	@Autowired
	private ProjectTaskService projectTaskService;

	@Autowired
	private ErrorMapService errorMapService;

	@PostMapping({ "/{projectIdentifier}" })
	public ResponseEntity<?> addNewProjectTaskToBacklog(@Valid @RequestBody ProjectTask projectTask,
			BindingResult result, @PathVariable String projectIdentifier,Principal principal) {

		ResponseEntity<Map<String, String>> errorMap = errorMapService.mapErrors(result);
		if (errorMap != null)
			return errorMap;

		ProjectTask savedProjectTask = projectTaskService.addProjectTaskToBacklog(projectIdentifier.toUpperCase(),
				projectTask,principal);
		return new ResponseEntity<ProjectTask>(savedProjectTask, HttpStatus.CREATED);

	}

	@GetMapping({ "/{projectIdentifier}" })
	public ResponseEntity<List<ProjectTask>> getAllProjectTasks(@PathVariable String projectIdentifier,Principal principal) {
		List<ProjectTask> allProjectTask = projectTaskService.getAllProjectTasks(projectIdentifier.toUpperCase(),principal);
		return new ResponseEntity<List<ProjectTask>>(allProjectTask, HttpStatus.OK);

	}

	@GetMapping({ "/{projectIdentifier}/projectTask/{projectTaskSequence}" })
	public ResponseEntity<?> getProjectTaskById(@PathVariable String projectIdentifier,
			@PathVariable String projectTaskSequence,Principal principal) {
		
		ProjectTask foundProjectTask = projectTaskService.getProjectTaskByProjectTaskSequence(
				projectIdentifier.toUpperCase(), projectTaskSequence.toUpperCase(),principal);
		return new ResponseEntity<ProjectTask>(foundProjectTask, HttpStatus.OK);
	}
	
	
	@PutMapping({"/{projectIdentifier}/projectTask/{projectTaskSequence}"})
	public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask updatedProjectTask,BindingResult result, @PathVariable String projectIdentifier,
			@PathVariable String projectTaskSequence,Principal principal){
			
		ResponseEntity<Map<String, String>> errorMap = errorMapService.mapErrors(result);
		if (errorMap != null)
			return errorMap;
		
		ProjectTask returnedProjectTask=projectTaskService.updateProjectTask(projectIdentifier, projectTaskSequence, updatedProjectTask,principal);
		return new ResponseEntity<ProjectTask>(returnedProjectTask,HttpStatus.OK);
		
		
	}

	@DeleteMapping({ "/{projectIdentifier}/projectTask/{projectTaskSequence}" })
	public ResponseEntity<?> deleteProjectTask(@PathVariable String projectIdentifier,
			@PathVariable String projectTaskSequence,Principal principal) {

		projectTaskService.deleteProjectTask(projectIdentifier, projectTaskSequence,principal);
		return new ResponseEntity<String>("Project task deleted successfully", HttpStatus.OK);
	}
	
	
	@GetMapping({"/currentUser"})
	public ResponseEntity<?> ListAssignedProjectTasks(Principal principal){
		List<ProjectTask> projectTasks=projectTaskService.getAssignedProjectTasks(principal);
		return new ResponseEntity<List<ProjectTask>>(projectTasks,HttpStatus.OK);
	
	}


}
