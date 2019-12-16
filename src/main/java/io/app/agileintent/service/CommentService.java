package io.app.agileintent.service;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Service;

import io.app.agileintent.domain.Comment;

@Service
public interface CommentService {

	public Comment addComment(String projectIdentifier, String projectTaskIdentifier, Comment comment,
			Principal principal);

	public List<Comment> listAllComments(String projectIdentifier, String projectTaskIdentifier, Principal principal);

	public Comment editComment(String projectIdentifier, String projectTaskSequence,Long commentId ,Comment comment,
			Principal principal);

	public void deleteComment(String projectIdentifier, String projectTaskSequence,Long commentId,Principal prinicipal);

	public Comment findCommentById(String projectIdentifier, String projectTaskIdentifier, Long id,
			Principal principal);
}
