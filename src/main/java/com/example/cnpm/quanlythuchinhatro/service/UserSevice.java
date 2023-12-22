package com.example.cnpm.quanlythuchinhatro.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.cnpm.quanlythuchinhatro.dto.UpdateUserRequest;
import com.example.cnpm.quanlythuchinhatro.dto.UserLoginRequest;
import com.example.cnpm.quanlythuchinhatro.dto.UserRequest;
import com.example.cnpm.quanlythuchinhatro.model.User;

public interface UserSevice {
	ResponseEntity<?> registerUser(UserRequest userRequest);
	
	User findByUsername(String username);
	
	String login(String username, String password);
	
	ResponseEntity<?> changePassword(UserLoginRequest loginRequest, String newPassword);
	
	void logout(String username);
	
	User updateUser(String username, UpdateUserRequest updateUserRequest);
	
	ResponseEntity<String> resetPassword(String username, String newPassword);
	
	String addUser(UserRequest userRequest);
}
