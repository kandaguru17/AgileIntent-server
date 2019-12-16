package io.app.agileintent.exceptions;

public class CommentExceptionMessage {

	private String commentException;
	
	public CommentExceptionMessage(String commentException) {
		this.commentException = commentException;
	}

	public String getCommentException() {
		return commentException;
	}

	public void setCommentException(String commentException) {
		this.commentException = commentException;
	}

}
