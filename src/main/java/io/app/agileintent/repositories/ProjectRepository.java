package io.app.agileintent.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.app.agileintent.domain.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

	public Project findByProjectIdentifier(String projectIdentifier);
	//public List<Project> findAllByReportingPerson(String username); 
	@Query(value = "select * from project where id IN (select project_id from user_project where user_id=:userId);",nativeQuery = true)
	List<Project> fetchProjects(@Param("userId") Long userId);
	
	
}
