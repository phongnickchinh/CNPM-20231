package com.example.cnpm.quanlythuchinhatro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cnpm.quanlythuchinhatro.model.SecurityQuestion;
import com.example.cnpm.quanlythuchinhatro.model.User;

public interface SecurityQuestionRepository extends JpaRepository<SecurityQuestion, Integer> {

	void deleteAllByUser(User user);
	
	SecurityQuestion findByUserAndQuestion(User user, String question);
	
}
