package io.app.agileintent.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler
	public final ResponseEntity<?> handleProjectIdException(ProjectIdException ex, WebRequest req) {
		System.out.println(ex.getMessage());
		ProjectIdExceptionMessage projectIdExceptionRepsonse = new ProjectIdExceptionMessage(ex.getMessage());
		return new ResponseEntity<ProjectIdExceptionMessage>(projectIdExceptionRepsonse, HttpStatus.BAD_REQUEST);
	}

	
	@ExceptionHandler
	public final ResponseEntity<?> handleBacklogNotFoundException(ProjectNotFoundException ex,WebRequest req){
		ProjectNotFoundExceptionMessage backlogExceptionMessage= new ProjectNotFoundExceptionMessage(ex.getBacklogExceptionMessage());
		return new ResponseEntity<ProjectNotFoundExceptionMessage>(backlogExceptionMessage,HttpStatus.BAD_REQUEST);
		
	}
	
}
