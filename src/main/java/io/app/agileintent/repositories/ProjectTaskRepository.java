package io.app.agileintent.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.app.agileintent.domain.ProjectTask;

@Repository
public interface ProjectTaskRepository extends JpaRepository<ProjectTask, Long> {

		
	public List<ProjectTask> findAllByProjectIdentifierOrderByPriority(String projectIdentifier);
	public ProjectTask findByProjectTaskSequence(String projectTaskSequence);
}
