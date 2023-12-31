package com.example.cnpm.quanlythuchinhatro.service;


import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.cnpm.quanlythuchinhatro.dto.ChangePasswordRequest;
import com.example.cnpm.quanlythuchinhatro.dto.ForgotPasswordRequest;
import com.example.cnpm.quanlythuchinhatro.dto.LoginRequest;
import com.example.cnpm.quanlythuchinhatro.dto.UpdateUserRequest;
import com.example.cnpm.quanlythuchinhatro.dto.UserSignUpRequest;
import com.example.cnpm.quanlythuchinhatro.model.SecurityQuestion;
import com.example.cnpm.quanlythuchinhatro.model.User;
import com.example.cnpm.quanlythuchinhatro.repository.SecurityQuestionRepository;
import com.example.cnpm.quanlythuchinhatro.repository.UserRepository;

	@Service
	public class UserServiceImpl implements UserSevice {
	
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
	 
	 @Override
	    public ResponseEntity<?> updateUser(UpdateUserRequest updateUserRequest) {
	        Optional<User> optionalUser = userRepository.findByUsername(updateUserRequest.getUsername());

	        if (optionalUser.isPresent()) {
	            User user = optionalUser.get();
	            user.setName(updateUserRequest.getFullname());
	            user.setPhoneNumber(updateUserRequest.getPhoneNumber());
	            user.setBankName(updateUserRequest.getBankName());
	            user.setBankAccountNumber(updateUserRequest.getBankNumber());
	            user.setAvatarUrl(updateUserRequest.getAvatarUrl());

	            userRepository.save(user);

	            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
	        }
	    }

	 @Override
	    public ResponseEntity<?> changePassword(ChangePasswordRequest changePasswordRequest) {
	        String username = changePasswordRequest.getUsername();
	        String currentPassword = changePasswordRequest.getCurrentPassword();
	        String newPassword = changePasswordRequest.getNewPassword();

	        Optional<User> optionalUser = userRepository.findByUsername(username);

	        if (optionalUser.isPresent()) {
	            User user = optionalUser.get();

	            // Kiểm tra mật khẩu hiện tại
	            if (passwordEncoder.matches(currentPassword, user.getPassword())) {
	                // Cập nhật mật khẩu mới
	                user.setPassword(passwordEncoder.encode(newPassword));
	                userRepository.save(user);

	                return new ResponseEntity<>("Password changed successfully", HttpStatus.OK);
	            } else {
	                return new ResponseEntity<>("Current password is incorrect", HttpStatus.UNAUTHORIZED);
	            }
	        } else {
	            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
	        }
	    }
	 
	 public ResponseEntity<String> forgotPassword(ForgotPasswordRequest request) {
	        // Tìm kiếm người dùng theo username
	        Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());
	        if (optionalUser.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
	        }

	        User user = optionalUser.get();

	        // Kiểm tra câu trả lời bảo mật
	        List<SecurityQuestion> securityQuestions = securityQuestionRepository.findByUserId(user.getId());
	        boolean isAnswerCorrect = securityQuestions.stream()
	                .anyMatch(sq -> sq.getAnswer().equals(request.getSecurityAnswer()));

	        if (!isAnswerCorrect) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect security answer");
	        }

	        // Đặt lại mật khẩu và lưu vào cơ sở dữ liệu
	        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
	        userRepository.save(user);

	        return ResponseEntity.ok("Password reset successfully");
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
            // Các trường thông tin khác
            return userInfo;
        } else {
            // Xử lý trường hợp không tìm thấy người dùng
            return null;
        }
    }
}
