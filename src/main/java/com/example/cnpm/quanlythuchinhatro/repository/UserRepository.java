package com.example.cnpm.quanlythuchinhatro.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.cnpm.quanlythuchinhatro.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	public Optional<User> findByUsername(String username);

	@Query("SELECT id FROM User WHERE username = :username ")
	Integer convertUsernameToUserId(String username);

	@Query(value = "SELECT u.* FROM member_of_room mr JOIN user u ON mr.user_id = u.id WHERE mr.room_id =:roomId", nativeQuery = true)
	List<User> getAllMemberOfRoom(Integer roomId);

}
