package io.app.agileintent.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.app.agileintent.domain.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

	public Project findByProjectIdentifier(String projectIdentifier);
	
}
