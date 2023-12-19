package com.example.cnpm.quanlythuchinhatro.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.cnpm.quanlythuchinhatro.dto.UpdateUserRequest;
import com.example.cnpm.quanlythuchinhatro.dto.UserLoginRequest;
import com.example.cnpm.quanlythuchinhatro.dto.UserRequest;
import com.example.cnpm.quanlythuchinhatro.model.User;
import com.example.cnpm.quanlythuchinhatro.repository.UserRepository;

	@Service
	public class UserServiceImpl implements UserSevice {
	
	 @Autowired
	 private UserRepository userRepository;
	
	 @Autowired
	 private PasswordEncoder passwordEncoder;
	 
	
	 @Override
	 public ResponseEntity<?> registerUser(UserRequest userRequest) {
	     // Kiểm tra xem username đã tồn tại hay chưa
	     if (userRepository.findByUsername(userRequest.getUsername()) != null) {
	         return ResponseEntity.status(401).body("EXISTED_USERNAME");
	     }
	
	     // Mã hóa mật khẩu trước khi lưu vào cơ sở dữ liệu
	     String encodedPassword = passwordEncoder.encode(userRequest.getPassword());
	
	     // Tạo đối tượng User từ UserRequest
	     User user = new User();
	     user.setName(userRequest.getFullname());
	     user.setUsername(userRequest.getUsername());
	     user.setPassword(encodedPassword);
	
	     // Lưu người dùng mới vào cơ sở dữ liệu
	     userRepository.save(user);
	
	     return ResponseEntity.ok().build();
	 }
	 
	 @Override
	 public String login(String username, String password) {
	     User user = userRepository.findByUsername(username);
	
	     if (user == null) {
	         return "USERNAME_DOESNOT_EXIST";
	     }
	
	     if (!user.getPassword().equals(password)) {
	         return "WRONG_PASSWORD";
	     }
	
	     return "Login successful";
	 }
	 
	 @Override
	    public void logout(String username) {
	    }
	 
	 @Override
	    public User updateUser(String username, UpdateUserRequest updateUserRequest) {
	        User user = userRepository.findByUsername(username);

	        if (user != null) {
	            // Cập nhật thông tin người dùng
	            user.setName(updateUserRequest.getFullname());
	            user.setPhoneNumber(updateUserRequest.getPhoneNumber());
	            user.setBankName(updateUserRequest.getBankName());
	            user.setBankAccountNumber(updateUserRequest.getBankNumber());
	            user.setAvatarUrl(updateUserRequest.getAvatarUrl());

	            // Lưu thông tin cập nhật vào cơ sở dữ liệu
	            userRepository.save(user);

	            return user;
	        }

	        return null; // Hoặc có thể ném một ngoại lệ để xử lý tình huống người dùng không tồn tại
	    }

	 @Override
	    public ResponseEntity<?> changePassword(UserLoginRequest loginRequest, String newPassword) {
	        User user = userRepository.findByUsername(loginRequest.getUsername());

	        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
	            // Mật khẩu hiện tại hợp lệ
	            user.setPassword(passwordEncoder.encode(newPassword)); // Mã hóa mật khẩu mới
	            userRepository.save(user);
	            return new ResponseEntity<>("Password changed successfully", HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Invalid current password", HttpStatus.UNAUTHORIZED);
	        }
	    }
	 
	 @Override
	    public ResponseEntity<String> resetPassword(String username, String newPassword) {
	        User user = userRepository.findByUsername(username);

	        if (user != null) {
	            user.setPassword(passwordEncoder.encode(newPassword));
	            userRepository.save(user);
	            return new ResponseEntity<>("Password reset successfully", HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
	        }
	    }
}
