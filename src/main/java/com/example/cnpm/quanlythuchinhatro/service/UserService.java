package com.example.cnpm.quanlythuchinhatro.service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.example.cnpm.quanlythuchinhatro.dto.LoginRequest;
import com.example.cnpm.quanlythuchinhatro.dto.UpdateUserRequest;
import com.example.cnpm.quanlythuchinhatro.dto.UserSignUpRequest;
import com.example.cnpm.quanlythuchinhatro.model.User;

public interface UserService {
	
	ResponseEntity<String> signUp(UserSignUpRequest userSignUpRequest);

	Optional<User> findUserByUsername(String username);

	ResponseEntity<?> login(LoginRequest userLoginRequest);

	UpdateUserRequest getUserInfo(String username);
	
}
