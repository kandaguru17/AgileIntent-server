package io.app.agileintent.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.app.agileintent.domain.ProjectTask;

@Repository
public interface ProjectTaskRepository extends JpaRepository<ProjectTask, Long> {

		
	public List<ProjectTask> findAllByProjectIdentifierOrderByPriority(String projectIdentifier);
	public ProjectTask findByProjectTaskSequence(String projectTaskSequence);
	
	@Query(value = "select * from project_task where assigned_to=:userId and status <> :status",nativeQuery = true)
	List<ProjectTask> findAssignedProjectTasks(@Param("userId") Long userId,@Param("status") String status);
}
