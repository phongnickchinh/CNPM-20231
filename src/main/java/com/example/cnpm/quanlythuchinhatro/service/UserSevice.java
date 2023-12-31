package com.example.cnpm.quanlythuchinhatro.service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.cnpm.quanlythuchinhatro.dto.ChangePasswordRequest;
import com.example.cnpm.quanlythuchinhatro.dto.ForgotPasswordRequest;
import com.example.cnpm.quanlythuchinhatro.dto.LoginRequest;
import com.example.cnpm.quanlythuchinhatro.dto.UpdateUserRequest;
import com.example.cnpm.quanlythuchinhatro.dto.UserSignUpRequest;
import com.example.cnpm.quanlythuchinhatro.model.User;

public interface UserSevice {
	
	ResponseEntity<String> signUp(UserSignUpRequest userSignUpRequest);
	
	ResponseEntity<?> updateUser(UpdateUserRequest updateUserRequest);
	
	ResponseEntity<?> changePassword(ChangePasswordRequest changePasswordRequest);
	
	ResponseEntity<String> forgotPassword(ForgotPasswordRequest request);

	Optional<User> findUserByUsername(String username);
	
	ResponseEntity<?> login(LoginRequest userLoginRequest);

	UpdateUserRequest getUserInfo(String username);
	
}
