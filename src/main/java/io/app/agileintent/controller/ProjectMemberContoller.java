package io.app.agileintent.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.app.agileintent.domain.AddUserGroup;
import io.app.agileintent.domain.Project;
import io.app.agileintent.domain.User;
import io.app.agileintent.service.ErrorMapService;
import io.app.agileintent.service.ProjectMemberService;


@RestController
@RequestMapping("/api/members")
@CrossOrigin
public class ProjectMemberContoller {

	@Autowired
	private ErrorMapService errorMapService;
	@Autowired
	private ProjectMemberService projectMemberService;
	
	@PostMapping({"/{projectIdentifier}"})
	public ResponseEntity<?> addProjectMembers(@Validated({AddUserGroup.class}) @RequestBody User user,BindingResult result ,@PathVariable String projectIdentifier,Principal principal){
		
		ResponseEntity<Map<String,String>> error=errorMapService.mapErrors(result);
		if(error!=null)
			return error;
		
		Project project=projectMemberService.addUserToProject(projectIdentifier,user.getUsername(), principal);
		return new ResponseEntity<Project>(project,HttpStatus.OK);
	}

	@GetMapping({"/{projectIdentifier}"})
	public ResponseEntity<?> listProjectMembers(@PathVariable String projectIdentifier,Principal principal){
		List<User> projectUsers=projectMemberService.getProjectUsers(projectIdentifier, principal);
		return new ResponseEntity<List<User>>(projectUsers,HttpStatus.OK);
	}
}
