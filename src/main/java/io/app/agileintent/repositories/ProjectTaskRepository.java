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
	
	@Query(name = "select * from project_task where assigned_to=:userId",nativeQuery = true)
	List<ProjectTask> findAllByUserId(@Param("userId") Long userId);
}
