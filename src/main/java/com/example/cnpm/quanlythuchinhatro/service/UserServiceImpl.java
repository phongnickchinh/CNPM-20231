package com.example.cnpm.quanlythuchinhatro.service;


import java.util.Map;
import java.util.Optional;


import com.example.cnpm.quanlythuchinhatro.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.cnpm.quanlythuchinhatro.model.User;
import com.example.cnpm.quanlythuchinhatro.repository.SecurityQuestionRepository;
import com.example.cnpm.quanlythuchinhatro.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	 @Autowired
	 private UserRepository userRepository;
	
	 @Autowired
	 private PasswordEncoder passwordEncoder;
	 
	 @Autowired
	 private SecurityQuestionRepository securityQuestionRepository;

	 public ResponseEntity<String> signUp(UserSignUpRequest userSignUpRequest) {
		 if (userRepository.findByUsername(userSignUpRequest.getUsername()).isPresent()) {
			 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("EXISTED_USERNAME");
		 }
		 User user = new User(); 	
		 user.setName(userSignUpRequest.getFullname());
		 user.setUsername(userSignUpRequest.getUsername());
		 user.setPassword(passwordEncoder.encode(userSignUpRequest.getPassword()));
		 userRepository.save(user);

		 return ResponseEntity.ok("User registered successfully");
	 }
	 public ResponseEntity<?> login(LoginRequest userLoginRequest) {
	        Optional<User> optionalUser = userRepository.findByUsername(userLoginRequest.getUsername());

	        if (optionalUser.isPresent()) {
	            User user = optionalUser.get();
	            
	            // Kiểm tra mật khẩu
	            if (passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())) {
	                // Đăng nhập thành công
	                return ResponseEntity.ok(Map.of("message", "Login successfully"));
	            } else {
	                // Sai mật khẩu
	                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("WRONG_PASSWORD");
	            }
	        } else {
	            // Tên đăng nhập không tồn tại
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("USERNAME_DOESNOT_EXIST");
	        }
	    }

		public UpdateUserRequest getUserInfo(String username) {
			// Lấy thông tin người dùng từ repository và chuyển đổi thành đối tượng UserInfo
			Optional<User> optionalUser = userRepository.findByUsername(username);
			if (optionalUser.isPresent()) {
				User user = optionalUser.get();
				UpdateUserRequest userInfo = new UpdateUserRequest();
				userInfo.setUsername(user.getUsername());
				userInfo.setFullname(user.getName());
				userInfo.setPhoneNumber(user.getPhoneNumber());
				userInfo.setBankName(user.getBankName());
				userInfo.setBankNumber(user.getBankAccountNumber());
				userInfo.setAvatarUrl(user.getAvatarUrl());
				// Các trường thông tin khác
				return userInfo;
			} else {
				// Xử lý trường hợp không tìm thấy người dùng
				return null;
			}
		}

	public ResponseEntity<?> updateInfo(String username, UserDTO userDTO) {

		Optional<User> userDB = userRepository.findByUsername(username);

		if(userDB.isPresent()) {
			User user = userDB.get();
			if(userDTO.getPhoneNumber() != null ) {user.setPhoneNumber(userDTO.getPhoneNumber());}
			if(userDTO.getBankName() != null ) {user.setBankName(userDTO.getBankName());}
			if(userDTO.getName() != null ) {user.setName(userDTO.getName());}
			if(userDTO.getBankNumber() != null ) {user.setBankAccountNumber(userDTO.getBankNumber());}

			userRepository.save(user);
			return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","Cập nhật thông tin thành công"));
		} else {
			return ResponseEntity.badRequest().body("Cập nhât thông tin không thành công");
		}
	}
	
	 @Override
	 public ResponseEntity<?> changePassword(String username, ChangePasswordRequest changePasswordRequest) {
	        // Lấy thông tin người dùng từ repository
	        Optional<User> optionalUser = userRepository.findByUsername(username);
	        if (optionalUser.isPresent()) {
	            User user = optionalUser.get();
	            // Kiểm tra mật khẩu cũ
	            if (passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
	                // Cập nhật mật khẩu mới
	                user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
	                userRepository.save(user);
	                return ResponseEntity.ok("Password changed successfully");
	            } else {
	                // Trả về lỗi nếu mật khẩu cũ không đúng
	                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("WRONG_PASSWORD");
	            }
	        } else {
	            // Trả về lỗi nếu không tìm thấy người dùng
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
	        }
	    }
	 
}
