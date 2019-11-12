package io.app.agileintent.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import io.app.agileintent.domain.User;

public interface UserRepository extends JpaRepository<User,Long> {

	User findByUsername(String username);
}
