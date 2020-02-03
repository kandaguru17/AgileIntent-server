package io.app.agileintent.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.app.agileintent.domain.EmailConfirmation;

@Repository
public interface EmailConfirmationRepository extends JpaRepository<EmailConfirmation, Long> {

	public EmailConfirmation findByConfirmationToken(String token);

}
