package com.example.cnpm.quanlythuchinhatro.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.cnpm.quanlythuchinhatro.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	Optional<User> findByUsername(String username);
	@Query("SELECT id FROM User WHERE username = :username ")
	Integer convertUsernameToUserId (String username);
}
