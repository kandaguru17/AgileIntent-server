package io.app.agileintent.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import io.app.agileintent.domain.Backlog;

public interface BacklogRepository extends JpaRepository<Backlog, Long> {

	public Backlog findByProjectIdentifier(String projectIdentifier);
}
