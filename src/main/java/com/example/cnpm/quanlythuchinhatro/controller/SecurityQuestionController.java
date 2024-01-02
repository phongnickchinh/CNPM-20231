package com.example.cnpm.quanlythuchinhatro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cnpm.quanlythuchinhatro.dto.SecurityQuestionRequest;
import com.example.cnpm.quanlythuchinhatro.repository.UserRepository;
import com.example.cnpm.quanlythuchinhatro.service.SecurityQuestionService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/security-question")
public class SecurityQuestionController {

    @Autowired
    private SecurityQuestionService securityQuestionService;

    @PostMapping("/add")
    public ResponseEntity<?> addSecurityQuestion(@RequestBody SecurityQuestionRequest securityQuestionRequest, HttpSession session) {
        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        Object loggedInUser = session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            // Gọi phương thức addSecurityQuestion từ SecurityQuestionService
            return securityQuestionService.addSecurityQuestion(securityQuestionRequest, loggedInUser.toString());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No user is logged in");
        }
    }
    
    @GetMapping("/get")
    public ResponseEntity<?> getSecurityQuestions(HttpSession session) {
        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        Object loggedInUser = session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            // Gọi phương thức getSecurityQuestions từ SecurityQuestionService
            return securityQuestionService.getSecurityQuestions(loggedInUser.toString());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No user is logged in");
        }
    }
}
