package com.example.cnpm.quanlythuchinhatro.service;

//SecurityQuestionServiceImpl.java

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cnpm.quanlythuchinhatro.model.SecurityQuestion;
import com.example.cnpm.quanlythuchinhatro.model.User;
import com.example.cnpm.quanlythuchinhatro.repository.SecurityQuestionRepository;
import com.example.cnpm.quanlythuchinhatro.repository.UserRepository;

import java.util.List;

@Service
public class SecurityQuestionServiceImpl implements SecurityQuestionService {

 @Autowired
 private SecurityQuestionRepository securityQuestionRepository;

 @Autowired
 private UserRepository userRepository;

 @Override
 public List<SecurityQuestion> getSecurityQuestionsByUsername(String username) {
     User user = userRepository.findByUsername(username);
     return user != null ? user.getSecurityQuestions() : null;
 }

 @Override
 public void saveSecurityQuestions(String username, List<SecurityQuestion> securityQuestions) {
     User user = userRepository.findByUsername(username);

     if (user != null) {
         // Xóa câu hỏi bảo mật cũ của người dùng
         securityQuestionRepository.deleteAllByUser(user);

         // Lưu câu hỏi bảo mật mới
         for (SecurityQuestion question : securityQuestions) {
             question.setUser(user);
             securityQuestionRepository.save(question);
         }
     }
 }

 @Override
 public void updateSecurityQuestions(String username, List<SecurityQuestion> securityQuestions) {
     // Tương tự như saveSecurityQuestions, nhưng không xóa câu hỏi cũ mà chỉ cập nhật
     User user = userRepository.findByUsername(username);

     if (user != null) {
         for (SecurityQuestion question : securityQuestions) {
             question.setUser(user);
             securityQuestionRepository.save(question);
         }
     }
 }

 @Override
 public void deleteSecurityQuestions(String username) {
     User user = userRepository.findByUsername(username);
     if (user != null) {
         securityQuestionRepository.deleteAllByUser(user);
     }
 }
 
 @Override
 public boolean verifySecurityAnswers(String username, List<SecurityQuestion> answers) {
     User user = userRepository.findByUsername(username);

     if (user != null) {
         for (SecurityQuestion answer : answers) {
             SecurityQuestion storedAnswer = securityQuestionRepository.findByUserAndQuestion(user, answer.getQuestion());
             if (storedAnswer == null || !storedAnswer.getAnswer().equals(answer.getAnswer())) {
                 return false; // Một trong những câu trả lời không đúng
             }
         }
         return true; // Tất cả câu trả lời đều đúng
     }
     return false; // Người dùng không tồn tại
 }
}
