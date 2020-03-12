package io.app.agileintent.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import io.app.agileintent.model.UsernameModel;
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
    public ResponseEntity<?> addProjectMembers(@Valid @RequestBody UsernameModel user,
                                               BindingResult result,
                                               @PathVariable String projectIdentifier, Principal principal) {

        ResponseEntity<Map<String, String>> error = errorMapService.mapErrors(result);
        if (error != null)
            return error;

        User addedUser = projectMemberService.addUserToProject(projectIdentifier, user.getUsername(), principal);
        return new ResponseEntity<User>(addedUser, HttpStatus.OK);
    }

    @DeleteMapping({"/{projectIdentifier}"})
    public ResponseEntity<?> removeProjectMember(@Valid @RequestBody UsernameModel user,
                                                 BindingResult result,
                                                 @PathVariable String projectIdentifier, Principal principal) {

        ResponseEntity<Map<String, String>> error = errorMapService.mapErrors(result);
        if (error != null)
            return error;

        User removedUser = projectMemberService.removeUserFromProject(projectIdentifier, user.getUsername(), principal);
        return new ResponseEntity<User>(removedUser, HttpStatus.OK);

    }

    @GetMapping({"/{projectIdentifier}"})
    public ResponseEntity<?> listProjectMembers(
            @PathVariable String projectIdentifier, Principal principal) {

        List<User> projectUsers = projectMemberService
                .getProjectUsers(projectIdentifier, principal);

        return new ResponseEntity<List<User>>(projectUsers, HttpStatus.OK);
    }


    @GetMapping({"search/{projectIdentifier}"})
    public ResponseEntity<?> searchProjectMembers(
            @PathVariable String projectIdentifier, @PathParam("firstName") String firstName, Principal principal) {
        List<User> projectUsers = projectMemberService
                .getProjectUsersByFirstName(projectIdentifier, firstName, principal);

        return new ResponseEntity<List<User>>(projectUsers, HttpStatus.OK);
    }


    @PostMapping("assign/{projectIdentifier}/{projectTaskSequence}")
    public ResponseEntity<?> assignUserToProjectTask(@Valid @RequestBody UsernameModel usernameModel,
                                                     @PathVariable String projectIdentifier,
                                                     @PathVariable String projectTaskSequence, Principal principal) {

        User assignedUser = projectMemberService
                .assignUserToProjectTask(projectIdentifier, projectTaskSequence, usernameModel.getUsername(),
                        principal);
        return new ResponseEntity<User>(assignedUser, HttpStatus.OK);
    }

}
