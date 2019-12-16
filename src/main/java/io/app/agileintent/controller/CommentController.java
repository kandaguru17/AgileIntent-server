package io.app.agileintent.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.app.agileintent.domain.Comment;
import io.app.agileintent.service.CommentService;
import io.app.agileintent.service.ErrorMapService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@Autowired
	private ErrorMapService errorMapService;

	@PostMapping({ "/{projectIdentifier}/{projectTaskSequence}", "" })
	public ResponseEntity<?> addComment(@PathVariable String projectIdentifier,
			@PathVariable String projectTaskSequence, @Valid @RequestBody Comment comment, BindingResult result,
			Principal principal) {

		ResponseEntity<Map<String, String>> error = errorMapService.mapErrors(result);
		if (error != null)
			return error;
		Comment newComment = commentService.addComment(projectIdentifier, projectTaskSequence, comment, principal);
		return new ResponseEntity<Comment>(newComment, HttpStatus.CREATED);
	}

	@GetMapping({ "/{projectIdentifier}/{projectTaskSequence}" })
	public ResponseEntity<?> getAllComments(@PathVariable String projectIdentifier,
			@PathVariable String projectTaskSequence, Principal principal) {
		List<Comment> comments = commentService.listAllComments(projectIdentifier, projectTaskSequence, principal);
		return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
	}

	@GetMapping({ "/{projectIdentifier}/{projectTaskSequence}/{commentId}" })
	public ResponseEntity<?> getCommentById(@PathVariable String projectIdentifier,
			@PathVariable String projectTaskSequence, @PathVariable Long commentId, Principal principal) {
		Comment comment = commentService.findCommentById(projectIdentifier, projectTaskSequence, commentId, principal);
		return new ResponseEntity<Comment>(comment, HttpStatus.OK);
	}

	@PutMapping({ "/{projectIdentifier}/{projectTaskSequence}/{commentId}" })
	public ResponseEntity<?> editComment(@PathVariable String projectIdentifier,
			@PathVariable String projectTaskSequence, @PathVariable Long commentId, @Valid @RequestBody Comment comment,
			BindingResult result, Principal principal) {
		Comment updatedComment = commentService.editComment(projectIdentifier, projectTaskSequence, commentId, comment,
				principal);
		return new ResponseEntity<Comment>(updatedComment, HttpStatus.OK);

	}

	@DeleteMapping({ "/{projectIdentifier}/{projectTaskSequence}/{commentId}" })
	public ResponseEntity<?> deleteComment(@PathVariable String projectIdentifier,
			@PathVariable String projectTaskSequence, @PathVariable Long commentId, Principal principal) {
		commentService.deleteComment(projectIdentifier, projectTaskSequence, commentId, principal);
		return new ResponseEntity<String>("Comment Deleted Successfully", HttpStatus.OK);

	}
}
