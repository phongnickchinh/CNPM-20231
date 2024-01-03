package com.example.cnpm.quanlythuchinhatro.controller;

import com.example.cnpm.quanlythuchinhatro.dto.*;
import com.example.cnpm.quanlythuchinhatro.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cnpm.quanlythuchinhatro.service.UserService;

import jakarta.servlet.http.HttpSession;

import java.util.Map;

@RestController
@RequestMapping("")
public class UserController {

	 @Autowired
	 private UserService userService;

	 @PostMapping("/signup")
	 public ResponseEntity<String> signUp(@RequestBody UserSignUpRequest userSignUpRequest) {
		 return userService.signUp(userSignUpRequest);
	 }


	 @PostMapping("/login")
	 public ResponseEntity<?> login(@RequestBody LoginRequest userLoginRequest, HttpSession session) {
	        // Gọi phương thức login từ UserService
	        ResponseEntity<?> responseEntity = userService.login(userLoginRequest);

	        // Kiểm tra kết quả đăng nhập
	        if (responseEntity.getStatusCode() == HttpStatus.OK) {
	            // Nếu đăng nhập thành công, lưu tên đăng nhập vào session
	            session.setAttribute("loggedInUser", userLoginRequest.getUsername());
	        }

	        return responseEntity;
	 }
	@GetMapping("/logout")
		public ResponseEntity<String> logout(HttpSession session) {
			session.removeAttribute("loggedInUser");
			return new ResponseEntity<>("Đăng xuất thành công", HttpStatus.OK);
		}
	
		@GetMapping("/current-user")
		public ResponseEntity<String> getCurrentUser(HttpSession session) {
			Object loggedInUser = session.getAttribute("loggedInUser");
	
			if (loggedInUser != null) {
				String userInfo = "Current user: " + loggedInUser.toString();
				return ResponseEntity.ok(userInfo);
			} else {
				String unauthorizedMessage = "No user is logged in";
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(unauthorizedMessage);
			}
		}
	@GetMapping("/info")
	public ResponseEntity<?> getUserInfo(HttpSession session) {
		Object loggedInUser = session.getAttribute("loggedInUser");
		if (loggedInUser != null) {
			UpdateUserRequest userInfo = userService.getUserInfo(loggedInUser.toString());
			if (userInfo != null) {
				return ResponseEntity.ok(userInfo);
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve user information");
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No user is logged in");
		}
	}
	@PutMapping("/update")
	public ResponseEntity<?> updateInfo(HttpSession session, @RequestBody UserDTO userDTO) {
		 Object loggedInUser = session.getAttribute("loggedInUser");
		if (loggedInUser != null) {
			User userInfo = userService.updateInfo(loggedInUser.toString(), userDTO);
			if (userInfo != null) {
				return ResponseEntity.ok(userInfo);
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Failed to update user information"));
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "No user is logged in"));
		}
	}
	@PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest, HttpSession session) {
        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        Object loggedInUser = session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            // Thực hiện thay đổi mật khẩu
            ResponseEntity<?> responseEntity = userService.changePassword(loggedInUser.toString(), changePasswordRequest);
            return responseEntity;
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No user is logged in");
        }
    }
	@PutMapping("/update_avatar")
	public ResponseEntity<?> updateInfo(HttpSession session, @RequestBody UpdateAvatarRequest updateAvatarRequest) {
		 Object loggedInUser = session.getAttribute("loggedInUser");
		if (loggedInUser != null) {
			ResponseEntity<?> userInfo = userService.updateAvatar(loggedInUser.toString(), updateAvatarRequest);
			if (userInfo != null) {
				return ResponseEntity.ok(userInfo);
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Failed to update user information"));
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "No user is logged in"));
		}
	}
}


