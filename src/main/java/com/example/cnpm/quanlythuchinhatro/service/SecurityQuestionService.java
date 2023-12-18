package com.example.cnpm.quanlythuchinhatro.service;

import java.util.List;

import com.example.cnpm.quanlythuchinhatro.model.SecurityQuestion;

public interface SecurityQuestionService {
	List<SecurityQuestion> getSecurityQuestionsByUsername(String username);
	
    void saveSecurityQuestions(String username, List<SecurityQuestion> securityQuestions);
    
    void updateSecurityQuestions(String username, List<SecurityQuestion> securityQuestions);
    
    void deleteSecurityQuestions(String username);
    
    boolean verifySecurityAnswers(String username, List<SecurityQuestion> answers);
}