package com.example.cnpm.quanlythuchinhatro.controller;

//SecurityQuestionAdminController.java

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.cnpm.quanlythuchinhatro.model.SecurityQuestion;
import com.example.cnpm.quanlythuchinhatro.service.SecurityQuestionService;

import java.util.List;

@RestController
@RequestMapping("/security")
public class SecurityQuestionController {

 @Autowired
 private SecurityQuestionService securityQuestionService;

 // Endpoint để lấy danh sách câu hỏi bảo mật của người dùng
 @GetMapping("/questions/{username}")
 public ResponseEntity<List<SecurityQuestion>> getSecurityQuestions(@PathVariable String username) {
     List<SecurityQuestion> securityQuestions = securityQuestionService.getSecurityQuestionsByUsername(username);

     if (securityQuestions != null) {
         return new ResponseEntity<>(securityQuestions, HttpStatus.OK);
     } else {
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
     }
 }

 // Endpoint để thêm câu hỏi bảo mật cho người dùng
 @PostMapping("/questions/{username}")
 public ResponseEntity<String> saveSecurityQuestions(
         @PathVariable String username,
         @RequestBody List<SecurityQuestion> securityQuestions) {
     securityQuestionService.saveSecurityQuestions(username, securityQuestions);
     return new ResponseEntity<>("Security questions saved successfully", HttpStatus.OK);
 }

 // Endpoint để cập nhật câu hỏi bảo mật của người dùng
 @PutMapping("/questions/{username}")
 public ResponseEntity<String> updateSecurityQuestions(
         @PathVariable String username,
         @RequestBody List<SecurityQuestion> securityQuestions) {
     securityQuestionService.updateSecurityQuestions(username, securityQuestions);
     return new ResponseEntity<>("Security questions updated successfully", HttpStatus.OK);
 }

 // Endpoint để xóa câu hỏi bảo mật của người dùng
 @DeleteMapping("/questions/{username}")
 public ResponseEntity<String> deleteSecurityQuestions(@PathVariable String username) {
     securityQuestionService.deleteSecurityQuestions(username);
     return new ResponseEntity<>("Security questions deleted successfully", HttpStatus.OK);
 }
}

