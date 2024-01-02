package com.example.cnpm.quanlythuchinhatro.service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.example.cnpm.quanlythuchinhatro.dto.ChangePasswordRequest;
import com.example.cnpm.quanlythuchinhatro.dto.LoginRequest;
import com.example.cnpm.quanlythuchinhatro.dto.UpdateUserRequest;
import com.example.cnpm.quanlythuchinhatro.dto.UserSignUpRequest;
import com.example.cnpm.quanlythuchinhatro.model.User;

public interface UserService {
	
	ResponseEntity<String> signUp(UserSignUpRequest userSignUpRequest);

	ResponseEntity<?> login(LoginRequest userLoginRequest);

	UpdateUserRequest getUserInfo(String username);
	
	User updateInfo(String username, User user);

	ResponseEntity<?> changePassword(String username, ChangePasswordRequest changePasswordRequest);

	
}
