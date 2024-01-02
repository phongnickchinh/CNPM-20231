package com.example.cnpm.quanlythuchinhatro.service;


import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.cnpm.quanlythuchinhatro.dto.LoginRequest;
import com.example.cnpm.quanlythuchinhatro.dto.UpdateUserRequest;
import com.example.cnpm.quanlythuchinhatro.dto.UserSignUpRequest;
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
		 // Kiểm tra xem username đã tồn tại chưa
		 if (userRepository.findByUsername(userSignUpRequest.getUsername()).isPresent()) {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
		 }

		 // Tạo mới người dùng
		 User user = new User();
		 user.setName(userSignUpRequest.getFullname());
		 user.setUsername(userSignUpRequest.getUsername());
		 user.setPassword(passwordEncoder.encode(userSignUpRequest.getPassword()));
		 // Các trường khác của người dùng có thể thêm vào tại đây
		 // Lưu vào cơ sở dữ liệu
		 userRepository.save(user);

		 return ResponseEntity.ok("User registered successfully");
	 }
public Optional<User> findUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
public ResponseEntity<?> login(LoginRequest userLoginRequest) {
	// Tìm kiếm người dùng theo username
	Optional<User> optionalUser = userRepository.findByUsername(userLoginRequest.getUsername());
	if (optionalUser.isEmpty()) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
	}

	User user = optionalUser.get();

	// Kiểm tra mật khẩu
	if (passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())) {
		return ResponseEntity.ok("Login successful");
	} else {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
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

}
