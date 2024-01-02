package com.example.cnpm.quanlythuchinhatro.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<?> addSecurityQuestion(SecurityQuestionRequest securityQuestionRequest, String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            
            // Tạo đối tượng SecurityQuestion và lưu vào cơ sở dữ liệu
            SecurityQuestion securityQuestion = new SecurityQuestion();
            securityQuestion.setUserId(user.getId());
            securityQuestion.setQuestion(securityQuestionRequest.getQuestion());
            securityQuestion.setAnswer(securityQuestionRequest.getAnswer());

            securityQuestionRepository.save(securityQuestion);

            return ResponseEntity.ok("Security question added successfully");
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
                    .map(question -> new SecurityQuestionDTO(question.getQuestion()))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(securityQuestionDTOs);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
	
}

