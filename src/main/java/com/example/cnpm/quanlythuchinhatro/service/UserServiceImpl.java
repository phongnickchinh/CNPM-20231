package com.example.cnpm.quanlythuchinhatro.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.cnpm.quanlythuchinhatro.dto.UserRequest;
import com.example.cnpm.quanlythuchinhatro.model.User;
import com.example.cnpm.quanlythuchinhatro.repository.UserRepository;

	@Service
	public class UserServiceImpl implements UserSevice {
	
	 @Autowired
	 private UserRepository userRepository;
	
	 @Autowired
	 private BCryptPasswordEncoder passwordEncoder;
	
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
}
