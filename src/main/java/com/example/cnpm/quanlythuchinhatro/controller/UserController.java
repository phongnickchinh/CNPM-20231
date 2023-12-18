package com.example.cnpm.quanlythuchinhatro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.cnpm.quanlythuchinhatro.dto.UpdateUserRequest;
import com.example.cnpm.quanlythuchinhatro.dto.UserLoginRequest;
import com.example.cnpm.quanlythuchinhatro.dto.UserLogoutRequest;
import com.example.cnpm.quanlythuchinhatro.dto.UserRequest;
import com.example.cnpm.quanlythuchinhatro.model.SecurityQuestion;
import com.example.cnpm.quanlythuchinhatro.model.User;
import com.example.cnpm.quanlythuchinhatro.service.SecurityQuestionService;
import com.example.cnpm.quanlythuchinhatro.service.UserSevice;

@RestController
@RequestMapping("/")
public class UserController {

	 @Autowired
	 private UserSevice userService;
	 
	 @Autowired
	 private SecurityQuestionService securityQuestionService;
	
	 @PostMapping("signup")
	 public ResponseEntity<?> signup(@RequestBody UserRequest userRequest) {
	     return userService.registerUser(userRequest);
	 }
	 @PostMapping("login")
	    public ResponseEntity<String> login(@RequestBody UserLoginRequest userLoginRequest) {
	        String result = userService.login(userLoginRequest.getUsername(), userLoginRequest.getPassword());

	        if ("Login successful".equals(result)) {
	            return new ResponseEntity<>(result, HttpStatus.OK);
	        } else if ("USERNAME_DOESNOT_EXIST".equals(result)) {
	            return new ResponseEntity<>("USERNAME_DOESNOT_EXIST", HttpStatus.UNAUTHORIZED);
	        } else {
	            return new ResponseEntity<>("WRONG_PASSWORD", HttpStatus.UNAUTHORIZED);
	        }
	    }
	 @PostMapping("logout")
	    public ResponseEntity<String> logout(@RequestBody UserLogoutRequest userLogoutRequest) {
	        userService.logout(userLogoutRequest.getUsername());
	        return new ResponseEntity<>("Logout successful", HttpStatus.OK);
	    }

	    // Chuyển hướng về trang đăng nhập sau khi đăng xuất
	    @GetMapping("login")
	    public String redirectToLogin() {
	        return "redirect:/login";
	    }
	    
	    @PutMapping("user/{username}")
	    public ResponseEntity<User> updateUser(@PathVariable String username, @RequestBody UpdateUserRequest updateUserRequest) {
	        User updatedUser = userService.updateUser(username, updateUserRequest);

	        if (updatedUser != null) {
	            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }
	    
	    @PostMapping("user/change-password")
	    public ResponseEntity<?> changePassword(@RequestBody UserLoginRequest loginRequest, @RequestParam String newPassword) {
	        return userService.changePassword(loginRequest, newPassword);
	    }
	    
	    @PostMapping("/forgot-password")
	    public ResponseEntity<String> verifySecurityQuestions(
	            @RequestParam String username,
	            @RequestBody List<SecurityQuestion> answers) {
	        if (securityQuestionService.verifySecurityAnswers(username, answers)) {
	            // Câu trả lời đúng, cho phép người dùng đặt lại mật khẩu
	            return new ResponseEntity<>("Security questions verified successfully", HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Invalid security answers", HttpStatus.UNAUTHORIZED);
	        }
	    }

	    // Endpoint để đặt lại mật khẩu sau khi kiểm tra câu hỏi bảo mật
	    @PostMapping("/reset-password")
	    public ResponseEntity<String> resetPassword(
	            @RequestParam String username,
	            @RequestParam String newPassword) {
	        return userService.resetPassword(username, newPassword);
	    }
}

