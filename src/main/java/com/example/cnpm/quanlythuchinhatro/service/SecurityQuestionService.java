package com.example.cnpm.quanlythuchinhatro.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.cnpm.quanlythuchinhatro.dto.SecurityQuestionDTO;
import com.example.cnpm.quanlythuchinhatro.dto.SecurityQuestionRequest;
import com.example.cnpm.quanlythuchinhatro.model.SecurityQuestion;
import com.example.cnpm.quanlythuchinhatro.model.User;
import com.example.cnpm.quanlythuchinhatro.repository.SecurityQuestionRepository;
import com.example.cnpm.quanlythuchinhatro.repository.UserRepository;

@Service
public class SecurityQuestionService {

    @Autowired
    private SecurityQuestionRepository securityQuestionRepository;

    @Autowired
    private UserRepository userRepository;
    
	 @Autowired
	 private PasswordEncoder passwordEncoder;

	    public ResponseEntity<?> addSecurityQuestion(SecurityQuestionRequest securityQuestionRequest, String username) {
	        Optional<User> optionalUser = userRepository.findByUsername(username);

	        if (optionalUser.isPresent()) {
	            User user = optionalUser.get();

	            // Kiểm tra mật khẩu để xác nhận đentityều đúng
	            if (passwordEncoder.matches(securityQuestionRequest.getPassword(), user.getPassword())) {
	                SecurityQuestion securityQuestion = new SecurityQuestion();
	                securityQuestion.setUserId(user.getId());
	                securityQuestion.setUser(user);
	                securityQuestion.setQuestion(securityQuestionRequest.getQuestion());
	                securityQuestion.setAnswer(securityQuestionRequest.getAnswer());

	                securityQuestionRepository.save(securityQuestion);

	                return ResponseEntity.ok("Security question added successfully");
	            } else {
	                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("WRONG_PASSWORD");
	            }
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
	        }
	    }

    public ResponseEntity<?> getSecurityQuestions(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Lấy danh sách câu hỏi bảo mật theo userId
            List<SecurityQuestion> securityQuestions = securityQuestionRepository.findByUserId(user.getId());

            // Chuyển đổi danh sách câu hỏi sang danh sách SecurityQuestionDTO
            List<SecurityQuestionDTO> securityQuestionDTOs = securityQuestions.stream()
                    .map(question -> new SecurityQuestionDTO(question.getId(),question.getQuestion()))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(securityQuestionDTOs);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    public ResponseEntity<?> deleteSecurityQuestion(Integer questionId, String username) {
        Optional<SecurityQuestion> optionalSecurityQuestion = securityQuestionRepository.findById(questionId);

        if (optionalSecurityQuestion.isPresent()) {
            SecurityQuestion securityQuestion = optionalSecurityQuestion.get();

            // Kiểm tra xem người dùng có quyền xóa câu hỏi bảo mật này hay không
            if (securityQuestion.getUser().getUsername().equals(username)) {
                // Xóa câu hỏi bảo mật
                securityQuestionRepository.delete(securityQuestion);
                return ResponseEntity.ok("Security question deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You don't have permission to delete this security question");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Security question not found");
        }
    }
}

