package com.example.cnpm.quanlythuchinhatro.service;

import org.springframework.http.ResponseEntity;

import com.example.cnpm.quanlythuchinhatro.dto.UserRequest;

public interface UserSevice {
	ResponseEntity<?> registerUser(UserRequest userRequest);

	String login(String username, String password);
}
