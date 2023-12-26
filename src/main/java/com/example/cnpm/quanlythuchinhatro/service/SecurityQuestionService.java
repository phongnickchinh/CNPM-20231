package com.example.cnpm.quanlythuchinhatro.service;

import org.springframework.http.ResponseEntity;

import com.example.cnpm.quanlythuchinhatro.dto.SecurityQuestionDTO;
import com.example.cnpm.quanlythuchinhatro.model.SecurityQuestion;

import java.util.List;

public interface SecurityQuestionService {
	ResponseEntity<?> addSecurityQuestion(Integer userId, String question, String answer);
	List<String> getSecurityQuestionsByUserId(Integer userId);
	void deleteSecurityQuestions(Integer userId);
	 void updateSecurityQuestion(Integer userId, SecurityQuestionDTO updatedQuestion);
	
}