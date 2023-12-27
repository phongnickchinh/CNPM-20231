package com.example.cnpm.quanlythuchinhatro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.cnpm.quanlythuchinhatro.dto.ChangePasswordRequest;
import com.example.cnpm.quanlythuchinhatro.dto.ForgotPasswordRequest;
import com.example.cnpm.quanlythuchinhatro.dto.UpdateUserRequest;
import com.example.cnpm.quanlythuchinhatro.dto.UserSignUpRequest;
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
	 
	 @PostMapping("/signup")
	    public ResponseEntity<String> signUp(@RequestBody UserSignUpRequest userSignUpRequest) {
	        return userService.signUp(userSignUpRequest);
	    }
	 	 @PutMapping("/update")
	    public ResponseEntity<?> updateUser(@RequestBody UpdateUserRequest updateUserRequest) {
	        return userService.updateUser(updateUserRequest);
	    }
	    
	 	@PutMapping("/change-password")
	    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
	        return userService.changePassword(changePasswordRequest);
	    }

	 	@PostMapping("/forgot-password")
	    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {
	        return userService.forgotPassword(request);
	    }
}

