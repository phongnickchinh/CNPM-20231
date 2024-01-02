package com.example.cnpm.quanlythuchinhatro.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.cnpm.quanlythuchinhatro.model.SecurityQuestion;

public interface SecurityQuestionRepository extends JpaRepository<SecurityQuestion, Integer> {

	 List<SecurityQuestion> findByUserId(Integer userId);

	 @Transactional
	 void deleteByUserId(Integer userId);

	 Optional<SecurityQuestion> findByUserIdAndQuestion(Integer userId, String question);
	 
}
