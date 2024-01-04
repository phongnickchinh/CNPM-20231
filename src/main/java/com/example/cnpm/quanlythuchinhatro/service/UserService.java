package com.example.cnpm.quanlythuchinhatro.service;

import com.example.cnpm.quanlythuchinhatro.dto.*;
import org.springframework.http.ResponseEntity;

public interface UserService {
	
	ResponseEntity<String> signUp(UserSignUpRequest userSignUpRequest);

	ResponseEntity<?> login(LoginRequest userLoginRequest);

	UpdateUserRequest getUserInfo(String username);

	ResponseEntity<?> updateInfo(String username, UserDTO userDTO);

	ResponseEntity<?> changePassword(String username, ChangePasswordRequest changePasswordRequest);

	 ResponseEntity<?> updateAvatar(String username, UpdateAvatarRequest updateAvatarRequest);
	 
	 String getFullNameById(Integer userId);
	
}
