package com.example.cnpm.quanlythuchinhatro.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.cnpm.quanlythuchinhatro.dto.SecurityQuestionDTO;
import com.example.cnpm.quanlythuchinhatro.model.SecurityQuestion;
import com.example.cnpm.quanlythuchinhatro.service.SecurityQuestionService;

@RestController
@RequestMapping("/security-question")
public class SecurityQuestionController {

    @Autowired
    private SecurityQuestionService securityQuestionService;

    @PostMapping("/add")
    public ResponseEntity<?> addSecurityQuestion(@RequestBody SecurityQuestion request) {
        return securityQuestionService.addSecurityQuestion(request.getUserId(), request.getQuestion(), request.getAnswer());
    }
    
    @GetMapping("/get")
    public ResponseEntity<List<String>> getSecurityQuestions(@RequestParam Integer userId) {
        List<String> securityQuestions = securityQuestionService.getSecurityQuestionsByUserId(userId);
        return ResponseEntity.ok(securityQuestions);
    }
    
    
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteSecurityQuestions(@RequestParam Integer userId) {
        securityQuestionService.deleteSecurityQuestions(userId);
        return ResponseEntity.ok("Security questions deleted successfully.");
    }
    
    @PostMapping("/update")
    public ResponseEntity<String> updateSecurityQuestion(@RequestParam Integer userId, @RequestBody SecurityQuestionDTO updatedQuestion) {
        securityQuestionService.updateSecurityQuestion(userId, updatedQuestion);
        return ResponseEntity.ok("Security question updated successfully.");
    }
}
