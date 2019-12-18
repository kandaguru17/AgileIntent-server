package io.app.agileintent.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.app.agileintent.domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	@Query(name = "Select * from comment where project_task_id=:projectTaskId;", nativeQuery = true)
	public List<Comment> findCommentsByProjectTaskId(@Param("projectTaskId") Long projectTaskId);

}
