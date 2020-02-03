package io.app.agileintent.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.app.agileintent.domain.Attachment;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

	@Query(value = "select * from attachment where project_task_id=:projectTaskId",nativeQuery = true)
	public List<Attachment> fetchAttachments(@Param("projectTaskId") Long projectTaskId);
}
