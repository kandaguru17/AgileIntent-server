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

    @ExceptionHandler(ProjectIdException.class)
    public final ResponseEntity<?> handleProjectIdException(ProjectIdException ex, WebRequest req) {
        ProjectIdExceptionMessage projectIdExceptionRepsonse = new ProjectIdExceptionMessage(ex.getMessage());
        return new ResponseEntity<ProjectIdExceptionMessage>(projectIdExceptionRepsonse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public final ResponseEntity<?> handleBacklogNotFoundException(ProjectNotFoundException ex, WebRequest req) {
        ProjectNotFoundExceptionMessage backlogExceptionMessage = new ProjectNotFoundExceptionMessage(
                ex.getBacklogExceptionMessage());
        return new ResponseEntity<ProjectNotFoundExceptionMessage>(backlogExceptionMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserProfileException.class)
    public final ResponseEntity<?> handleUserAlreadyExistException(UserProfileException ex, WebRequest req) {
        UserProfileExceptionMessage userExceptionMessage = new UserProfileExceptionMessage(
                ex.getUserExecetpionMessage());
        return new ResponseEntity<UserProfileExceptionMessage>(userExceptionMessage, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CommentException.class)
    public final ResponseEntity<?> handleCommentException(CommentException ex, WebRequest req) {
        CommentExceptionMessage commentExceptionMessage = new CommentExceptionMessage(ex.getMessage());
        return new ResponseEntity<CommentExceptionMessage>(commentExceptionMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AttachmentException.class)
    public final ResponseEntity<?> handleAttachmentException(AttachmentException ex, WebRequest req) {
        AttachmentExceptionMessage commentExceptionMessage = new AttachmentExceptionMessage(ex.getMessage());
        return new ResponseEntity<AttachmentExceptionMessage>(commentExceptionMessage, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(Exception.class)
//    public final ResponseEntity<?> handleGenericExceptions(Exception ex, WebRequest req) {
//        GenericExceptionMessage exceptionMessage = new GenericExceptionMessage(ex.getMessage());
//        return ResponseEntity.badRequest().body(exceptionMessage);
//    }

}
