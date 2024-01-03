package com.example.cnpm.quanlythuchinhatro.service;

import java.util.Optional;

import com.example.cnpm.quanlythuchinhatro.dto.*;
import org.springframework.http.ResponseEntity;

import com.example.cnpm.quanlythuchinhatro.model.User;

public interface UserService {
	
	ResponseEntity<String> signUp(UserSignUpRequest userSignUpRequest);

	ResponseEntity<?> login(LoginRequest userLoginRequest);

	UpdateUserRequest getUserInfo(String username);

	User updateInfo(String username, UserDTO userDTO);

	ResponseEntity<?> changePassword(String username, ChangePasswordRequest changePasswordRequest);

	 ResponseEntity<?> updateAvatar(String username, UpdateAvatarRequest updateAvatarRequest);

	
}
