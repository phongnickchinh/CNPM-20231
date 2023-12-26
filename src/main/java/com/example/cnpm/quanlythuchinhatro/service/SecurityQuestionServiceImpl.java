package com.example.cnpm.quanlythuchinhatro.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.cnpm.quanlythuchinhatro.dto.SecurityQuestionDTO;
import com.example.cnpm.quanlythuchinhatro.model.SecurityQuestion;
import com.example.cnpm.quanlythuchinhatro.repository.SecurityQuestionRepository;

@Service
public class SecurityQuestionServiceImpl implements SecurityQuestionService {

    @Autowired
    private SecurityQuestionRepository securityQuestionRepository;

    @Override
    public ResponseEntity<?> addSecurityQuestion(Integer userId, String question, String answer) {
        SecurityQuestion securityQuestion = new SecurityQuestion();
        securityQuestion.setUserId(userId);
        securityQuestion.setQuestion(question);
        securityQuestion.setAnswer(answer);

        securityQuestionRepository.save(securityQuestion);

        return new ResponseEntity<>("Security question added successfully", HttpStatus.OK);
    }
    
    @Override
    public List<String> getSecurityQuestionsByUserId(Integer userId) {
        List<SecurityQuestion> securityQuestions = securityQuestionRepository.findByUserId(userId);
        return securityQuestions.stream()
                .map(SecurityQuestion::getQuestion)
                .collect(Collectors.toList());
    }
    
    
    @Override
    public void deleteSecurityQuestions(Integer userId) {
        securityQuestionRepository.deleteByUserId(userId);
    }
    
    @Override
    public void updateSecurityQuestion(Integer userId, SecurityQuestionDTO updatedQuestion) {
        // Lấy câu hỏi và đáp án cũ
        SecurityQuestion existingQuestion = securityQuestionRepository
                .findByUserIdAndQuestion(userId, updatedQuestion.getQuestion())
                .orElseThrow(() -> new RuntimeException("Question not found"));

        // Kiểm tra câu trả lời đúng trước đó
        if (!existingQuestion.getAnswer().equals(updatedQuestion.getOldAnswer())) {
            throw new RuntimeException("Incorrect previous answer");
        }

        // Cập nhật đáp án mới
        existingQuestion.setAnswer(updatedQuestion.getNewAnswer());
        securityQuestionRepository.save(existingQuestion);
    }
}
