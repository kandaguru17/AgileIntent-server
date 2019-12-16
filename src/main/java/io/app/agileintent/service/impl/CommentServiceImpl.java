package io.app.agileintent.service.impl;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.app.agileintent.domain.Comment;
import io.app.agileintent.domain.ProjectTask;
import io.app.agileintent.domain.User;
import io.app.agileintent.exceptions.CommentException;
import io.app.agileintent.exceptions.UserProfileException;
import io.app.agileintent.repositories.CommentRepository;
import io.app.agileintent.repositories.UserRepository;
import io.app.agileintent.service.CommentService;
import io.app.agileintent.service.ProjectTaskService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private ProjectTaskService projectTaskService;

	@Autowired
	private UserRepository userRepository;

	@Override
	public Comment addComment(String projectIdentifier, String projectTaskSequence, Comment comment,
			Principal principal) {
		
		ProjectTask projectTask = projectTaskService.getProjectTaskByProjectTaskSequence(projectIdentifier,
				projectTaskSequence, principal);
		User user = userRepository.findByUsername(principal.getName());
		projectTask.addComment(comment);
		user.addComment(comment);		
		return commentRepository.save(comment);
	}

	@Override
	public List<Comment> listAllComments(String projectIdentifier, String projectTaskSequence, Principal principal) {
		ProjectTask projectTask=projectTaskService.getProjectTaskByProjectTaskSequence(projectIdentifier, projectTaskSequence, principal);
		return commentRepository.findCommentsByProjectTaskId(projectTask.getId());

	}

	@Override
	public Comment editComment(String projectIdentifier, String projectTaskSequence,Long commentId, Comment comment,
			Principal principal) {
		
		Comment foundComment=findCommentById(projectIdentifier, projectTaskSequence, commentId, principal);
		foundComment.setCommentText(comment.getCommentText());
		return commentRepository.save(foundComment);
	}

	@Override
	public void deleteComment(String projectIdentifier, String projectTaskSequence,Long commentId,Principal principal) {
		Comment comment=findCommentById(projectIdentifier, projectTaskSequence, commentId, principal);
		ProjectTask projectTask=projectTaskService.getProjectTaskByProjectTaskSequence(projectIdentifier, projectTaskSequence, principal);
		User user=userRepository.findByUsername(principal.getName());
		if(user==null) 
			throw new UserProfileException("No such user");
			
		projectTask.removeComment(comment);
		user.removeComment(comment);
		
		commentRepository.delete(comment);
	}

	@Override
	public Comment findCommentById(String projectIdentifier, String projectTaskSequence,Long commentId,Principal principal) {
		 projectTaskService.getProjectTaskByProjectTaskSequence(projectIdentifier,
				projectTaskSequence, principal);
		Comment comment=commentRepository.findById(commentId).orElse(null);
		if(comment==null)
			throw new CommentException("No such Comment");
		return comment;
	}

}
