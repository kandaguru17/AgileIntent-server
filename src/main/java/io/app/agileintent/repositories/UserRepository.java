package io.app.agileintent.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.app.agileintent.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);

	@Query(value = "select * from user where id in (select user_id from user_project where project_id=:projectId) and first_name like %:firstName%", nativeQuery = true)
	List<User> findUsersByMatchingFirstName(@Param("projectId")Long projectId, @Param("firstName") String firstName);

}
