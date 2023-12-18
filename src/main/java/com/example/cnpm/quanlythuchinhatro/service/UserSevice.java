package com.example.cnpm.quanlythuchinhatro.service;

import org.springframework.http.ResponseEntity;

import com.example.cnpm.quanlythuchinhatro.dto.UpdateUserRequest;
import com.example.cnpm.quanlythuchinhatro.dto.UserRequest;
import com.example.cnpm.quanlythuchinhatro.model.User;

public interface UserSevice {
	ResponseEntity<?> registerUser(UserRequest userRequest);

	String login(String username, String password);
	
	void logout(String username);
	
	User updateUser(String username, UpdateUserRequest updateUserRequest);

}
